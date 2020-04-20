package apple.npc.data.player;

import apple.npc.data.all.AllPlayers;
import apple.npc.data.convo.VariableChange;
import apple.npc.data.npc.NPCData;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerData {
    private HashMap<String, VariableCategory> variables;
    public String uid;

    public PlayerData(YamlConfiguration config) {
        variables = new HashMap<>();
        String uid = config.getString(YMLPlayerVariable.PLAYER_UID);
        this.uid = uid;
        ConfigurationSection varConfig = config.getConfigurationSection(YMLPlayerVariable.VARIABLES);
        Set<String> globalCategories = varConfig.getKeys(false);
        for (String global : globalCategories) {
            variables.put(global, new VariableCategory(varConfig.getConfigurationSection(global)));
        }
    }

    public PlayerData(String playerUID) {
        this.uid = playerUID;
        this.variables = new HashMap<>();
    }

    public void doVariableChanges(NPCData npc, HashSet<VariableChange> variableChanges) {
        for (VariableChange variable : variableChanges) {
            if (variable.globalVar.equals("default"))
                npc.setConclusionVar(uid, new Variable(variable));
            else {
                if (!variables.containsKey(variable.globalVar)) {
                    variables.put(variable.globalVar, new VariableCategory());
                }
                variables.get(variable.globalVar).addVar(new Variable(variable));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (String var : variables.keySet()) {
            string.append(var);
            string.append("\n");
            string.append(variables.get(var).toString());
            string.append("\n");
        }
        return string.toString();
    }

    public int getVarVal(String varGlobal, int varUID) {
        if (variables.containsKey(varGlobal))
            return variables.get(varGlobal).getVarVal(varUID);
        else
            return AllPlayers.DEFAULT_PLAYER_VAR_VAL;
    }

    public HashMap<String, VariableCategory> getVariables() {
        return variables;
    }


}

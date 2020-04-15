package apple.npc.data.player;

import apple.npc.data.all.AllPlayers;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class VariableCategory {
    private Map<Integer, Variable> variables;

    public VariableCategory(ConfigurationSection config) {
        variables = new HashMap<>();
        Set<String> varUidList = config.getKeys(false);
        for (String varUid : varUidList) {
            int varUidInt;
            try {
                varUidInt = Integer.parseInt(varUid);
            } catch (NumberFormatException e) {
                System.err.println("not numeric");
                continue;
            }
            variables.put(varUidInt, new Variable(config.getConfigurationSection(varUid)));
        }
    }

    public VariableCategory() {
        variables = new HashMap<>();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Variable var : variables.values()) {
            string.append(var.toString());
            string.append("\n");
        }
        return string.toString();
    }

    public int getVarVal(int varUID) {
        if (variables.containsKey(varUID))
            return variables.get(varUID).value;
        else
            return AllPlayers.DEFAULT_PLAYER_VAR_VAL;
    }

    public void addVar(@NotNull Variable local) {
        variables.put(local.uid, local);
    }

    public List<Integer> getVarLocalUIDs(String localName) {
        List<Integer> list = new ArrayList<>();
        for (Variable var : variables.values()) {
            System.out.println(var.toString());
            if (var.name.equals(localName))
                list.add(var.uid);
        }
        return list;
    }

    public int getNextUID() {
        int nextUID = 0;
        while (variables.containsKey(nextUID)) {
            nextUID++;
        }
        return nextUID;
    }

    public Map<Integer, Variable> getVariables() {
        return variables;
    }
}

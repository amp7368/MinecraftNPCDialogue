package apple.npc.data.player;

import apple.npc.data.player.Variable;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariableCategory {
    private Map<Integer, Variable> variables;

    public VariableCategory(ConfigurationSection config) {
        variables = new HashMap<>();
        Set<String> varUidList = config.getKeys(false);
        for (String varUid : varUidList) {
            int conversationUID;
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
        return variables.get(varUID).value;
    }

    public void addVar(@NotNull Variable local) {
        if (!variables.containsKey(local.uid)) {
            variables.put(local.uid, local);
        }
    }
}

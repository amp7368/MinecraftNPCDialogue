package apple.npc.data.category;

import apple.npc.data.components.Variable;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariableCategory {
    private Map<Integer, Variable> variables;

    public VariableCategory(ConfigurationSection config) {
        variables = new HashMap<>();
        Set<String> varUidList = config.getKeys(false);
        for (String varUid : varUidList) {
            if (!StringUtils.isNumeric(varUid)) {
                continue;
            }
            int varUidInt = Integer.parseInt(varUid);
            variables.put(varUidInt, new Variable(config.getConfigurationSection(varUid)));
        }
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
}

package apple.npc.data.single;

import apple.npc.data.category.VariableCategory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Set;

public class PlayerData {
    private HashMap<String, VariableCategory> variables;
    public String uid;

    public PlayerData(YamlConfiguration config) {
        variables = new HashMap<>();
        String uid = config.getString("uid");
        this.uid = uid;
        ConfigurationSection varConfig = config.getConfigurationSection("variables");
        Set<String> globalCategories = varConfig.getKeys(false);
        for (String global : globalCategories) {
            variables.put(global, new VariableCategory(varConfig.getConfigurationSection(global)));
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
}

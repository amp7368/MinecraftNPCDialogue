package apple.npc.creation.from_data.player;

import apple.npc.data.player.Variable;
import apple.npc.data.player.VariableCategory;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class WritePlayerVars {
    public static void setConfig(ConfigurationSection config, VariableCategory variableCategory) {
        Map<Integer, Variable> variables = variableCategory.getVariables();
        for(Integer varUID: variables.keySet()){
            WritePlayerVariable.setConfig(config.createSection(String.valueOf(varUID)),variables.get(varUID));
        }
    }
}

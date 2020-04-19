package apple.npc.creation.from_data.vars;

import apple.npc.data.player.Variable;
import apple.npc.data.player.VariableCategory;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class WriteGlobalAll {
    public static void write(String dataFolder, String globalName, VariableCategory category) {
        File file = new File(String.format("%s%s%s%s%s%s", dataFolder, File.separator, YMLFileNavigate.VARIABLE_FOLDER,
                File.separator, globalName, YMLFileNavigate.YML));
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.err.println("There was an error making a new file the variables.");
                    return;
                }
            } catch (IOException e) {
                System.err.println("There was an error making a new file in the variables.");
                return;
            }
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Map<Integer, Variable> variables = category.getVariables();
        for (Integer varUID : variables.keySet()) {
            ConfigurationSection varConfig = config.createSection(String.valueOf(varUID));
            Variable variable = variables.get(varUID);
            varConfig.set(YMLPlayerVariable.VARIABLE_NAME, variable.name);
        }
        try {
            config.save(file);
        } catch (IOException e) {
            System.err.println("There was an error writing the variables global category.");
        }
    }
}

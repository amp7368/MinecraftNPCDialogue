package apple.npc.creation.from_scratch.player.single;

import apple.npc.creation.from_scratch.player.info.PlayerVarInfo;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreatePlayerVars {
    public static boolean set(String folder, String playerUID, PlayerVarInfo var) {
        File directory = new File(String.format("%s%s%s", folder, File.separator, YMLFileNavigate.PLAYER_FOLDER));
        File file = new File(directory, String.format("%s%s", playerUID, YMLFileNavigate.YML));
        if (!file.exists())
            return false;
        YamlConfiguration configOrig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection config = configOrig.getConfigurationSection(YMLPlayerVariable.VARIABLES);

        if (config == null)
            return false;
        if (config.contains(var.global)) {
            config = config.getConfigurationSection(var.global);
        } else {
            config = config.createSection(var.global);
        }
        if (config == null)
            return false;
        config = config.createSection(String.valueOf(var.uid));
        config.set(YMLPlayerVariable.VARIABLE_UID, var.uid);
        config.set(YMLPlayerVariable.VARIABLE_NAME, var.name);
        config.set(YMLPlayerVariable.VARIABLE_VALUE, var.value);

        try {
            configOrig.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

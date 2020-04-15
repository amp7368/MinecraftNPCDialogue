package apple.npc.creation.from_scratch.player.single;

import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreatePlayerData {
    public static boolean create(String folder, String playerUID) {
        File directory = new File(String.format("%s%s%s", folder, File.separator, YMLFileNavigate.PLAYER_FOLDER));
        File file = new File(directory, String.format("%s%s", playerUID, YMLFileNavigate.YML));
        try {
            boolean didCreate = file.createNewFile();
            if (!didCreate)
                return false;
        } catch (IOException e) {
            return false;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(YMLPlayerVariable.PLAYER_UID, playerUID);
        config.createSection(YMLPlayerVariable.VARIABLES);
        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;

    }
}

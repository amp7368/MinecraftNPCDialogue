package apple.npc.creation.npc.single;

import apple.npc.creation.npc.info.NpcInfo;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateNpcData {
    public static boolean create(String folder, String globalName, NpcInfo npcInfo) {
        File directory = new File(String.format("%s%s%s", folder, File.separator, YMLFileNavigate.NPC_FOLDER));
        File file = new File(directory, String.format("%s%c%s%s", npcInfo.personalUID, ',', globalName, YMLFileNavigate.YML));
        try {
            boolean didCreate = file.createNewFile();
            if (!didCreate)
                return false;
        } catch (IOException e) {

            return false;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(YMLNpcNavigate.NPC_NAME, npcInfo.name);
        config.set(YMLNpcNavigate.NPC_UID, npcInfo.personalUID);
        config.set(YMLNpcNavigate.NPC_GAME_UID, npcInfo.gameUID);
        config.set(YMLNpcNavigate.STARTING_CONCLUSION, npcInfo.startingConclu);

        config.createSection(YMLNpcNavigate.START_LOCATION);
        config.createSection(YMLNpcNavigate.START_POSITION);
        config.createSection(YMLNpcNavigate.VARS_TO_CONCLUSIONS);
        config.createSection(YMLNpcNavigate.CONCLUSIONS_TO_CONVO);
        config.createSection(YMLNpcNavigate.PLAYERS_UID);


        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

package apple.npc.creation.from_scratch.convo.category;

import apple.npc.creation.from_scratch.convo.info.ConvoLocalInfo;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateConvoLocal {

    /**
     * creates a local category if it doesn't already exist
     *
     * @param folder     the folder where the file resides
     * @param globalName the file name
     * @param localInfo  the local category
     * @return whether we made that new local category
     */
    public static boolean create(String folder, String globalName, ConvoLocalInfo localInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER, File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String localInfoUidString = String.valueOf(localInfo.uid);
        if (config.contains(localInfoUidString)) {
            return false;
        }
        ConfigurationSection localConfig = config.createSection(localInfoUidString);
        localConfig.set(YMLConversationNavigate.LOCAL_UID, localInfo.uid);
        localConfig.set(YMLConversationNavigate.NAME, localInfo.name);
        localConfig.createSection(YMLConversationNavigate.CONVERSATIONS);

        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

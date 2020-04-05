package apple.npc.creation.convo.category;

import apple.npc.creation.convo.info.ConvoLocalInfo;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateConvoLocal {
    public static boolean create(String folder, String globalName, ConvoLocalInfo localInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER,File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String localInfoUidString = String.valueOf(localInfo.uid);
        if (config.contains(localInfoUidString)) {
            return false;
        }
        config.createSection(localInfoUidString);
        ConfigurationSection localConfig = config.getConfigurationSection(localInfoUidString);
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

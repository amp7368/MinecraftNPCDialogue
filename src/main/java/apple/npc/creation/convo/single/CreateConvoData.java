package apple.npc.creation.convo.single;

import apple.npc.creation.convo.info.ConvoDataInfo;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateConvoData {
    public static boolean create(String folder, String globalName, int localCategory, ConvoDataInfo convoInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER, File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection convoConfigOrig = config.getConfigurationSection(String.format("%d%s%s", localCategory, ".", YMLConversationNavigate.CONVERSATIONS));
        String convoInfoUidString = String.valueOf(convoInfo.uid);
        if(convoConfigOrig.contains(convoInfoUidString))
            return false;
        convoConfigOrig.createSection(convoInfoUidString);
        ConfigurationSection convoConfig = convoConfigOrig.getConfigurationSection(convoInfoUidString);
        convoConfig.set(YMLConversationNavigate.CONVERSATION_UID, convoInfo.uid);
        convoConfig.set(YMLConversationNavigate.NAME, convoInfo.name);
        convoConfig.set(YMLConversationNavigate.CONVERSATION_TEXT, convoInfo.text);
        convoConfig.createSection(YMLConversationNavigate.OPTIONS);
        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

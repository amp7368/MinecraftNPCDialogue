package apple.npc.creation.convo.single;

import apple.npc.creation.convo.components.CreatePostImmediate;
import apple.npc.creation.convo.info.ConvoDataInfo;
import apple.npc.creation.convo.info.ConvoRespPostInfo;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class CreateConvoData {
    /**
     * Creates a base conversation in the file
     *
     * @param folder        the base folder (not including CONVERSATION_FOLDER)
     * @param globalName    the global category in which the conversation resides
     * @param localCategory the local category in whic the conversation resides
     * @param convoInfo     the convoInfo about the base conversation
     * @return whether the creation was successful
     */
    public static boolean create(String folder, String globalName, int localCategory, ConvoDataInfo convoInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER, File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection convoConfigOrig = config.getConfigurationSection(String.format("%d%s%s", localCategory, ".", YMLConversationNavigate.CONVERSATIONS));
        String convoInfoUidString = String.valueOf(convoInfo.uid);
        if (convoConfigOrig == null) {
            System.out.println("convoConfigOrig is null in CreateConvoData");
            return false;
        }
        if (convoConfigOrig.contains(convoInfoUidString)) {
            System.out.println("convoInfoUID already exists in CreateConvoData");
            return false;
        }
        ConfigurationSection convoConfig = convoConfigOrig.createSection(convoInfoUidString);
        convoConfig.set(YMLConversationNavigate.CONVERSATION_UID, convoInfo.uid);
        convoConfig.set(YMLConversationNavigate.NAME, convoInfo.name);
        convoConfig.set(YMLConversationNavigate.CONVERSATION_TEXT, convoInfo.text);
        convoConfig.createSection(YMLConversationNavigate.OPTIONS);
        convoConfig.createSection(YMLConversationNavigate.IMMEDIATE_CONVO);
        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        CreatePostImmediate.set(folder, globalName, localCategory, convoInfo.uid,
                new ConvoRespPostInfo(0/*no clue what this is for*/, globalName, localCategory, convoInfo.uid,
                        new ArrayList<>()));
        return true;
    }
}

package apple.npc.creation.convo.components;

import apple.npc.creation.convo.info.ConvoRespPostInfo;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreatePostResponse {
    public static boolean create(String folder, String globalName, int localCategory, int conversationUID,
                                 int responseUID, ConvoRespPostInfo postInfo, Evaluateable redirectReqInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER,
                File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection postConfigOrig = config.getConfigurationSection(
                String.format("%d%s%s%s%d%s%s%s%d%c%s", localCategory, ".", YMLConversationNavigate.CONVERSATIONS, ".",
                        conversationUID, ".", YMLConversationNavigate.OPTIONS, ".", responseUID, '.', YMLConversationNavigate.POST_RESPONSES));
        if (postConfigOrig.contains(postInfo.responseId)) {
            return false;
        }
        postConfigOrig.createSection(postInfo.responseId);
        ConfigurationSection postConfig = postConfigOrig.getConfigurationSection(postInfo.responseId);
        CreateResponse.create(postConfig, postInfo, redirectReqInfo);

        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

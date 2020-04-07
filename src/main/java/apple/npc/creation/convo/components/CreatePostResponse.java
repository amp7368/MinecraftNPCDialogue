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
    /**
     * make a conversation postResponse to check before the defaultPostResponse
     *
     * @param folder          the base plugin folder (not including the CONVERSATION_FOLDER)
     * @param globalName      the global category of where this postResponse is going
     * @param localCategory   the local category uid of where this postResponse is going
     * @param conversationUID the conversation uid of where this postResponse is going
     * @param responseUID     the response uid of where this postResponse is going
     * @param postInfo        all the information about about the postResponse
     * @return whether the creation was successful
     */
    public static boolean create(String folder, String globalName, int localCategory, int conversationUID,
                                 int responseUID, ConvoRespPostInfo postInfo, Evaluateable redirectReqInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER,
                File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection postConfigOrig = config.getConfigurationSection(
                String.format("%d%s%s%s%d%s%s%s%d%c%s", localCategory, ".", YMLConversationNavigate.CONVERSATIONS, ".",
                        conversationUID, ".", YMLConversationNavigate.OPTIONS, ".", responseUID, '.', YMLConversationNavigate.POST_RESPONSES));
        if (postConfigOrig == null)
            return false;
        if (postConfigOrig.contains(postInfo.responseId)) {
            return false;
        }
        postConfigOrig.createSection(postInfo.responseId);
        ConfigurationSection postConfig = postConfigOrig.getConfigurationSection(postInfo.responseId);
        if (postConfig == null)
            return false;
        CreateResponse.create(postConfig, postInfo, redirectReqInfo);

        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

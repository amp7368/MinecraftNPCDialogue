package apple.npc.creation.convo.components;

import apple.npc.creation.convo.info.ConvoRespPostInfo;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * a class that deals with defaultPostResponse (very similar to CreatePostResponse class but just a defaultResponse)
 */
public class CreatePostDefault {
    /**
     * *Set* a conversation defaultPostResponse for when none of the postResponses work
     *
     * @param folder          the base plugin folder (not including the CONVERSATION_FOLDER)
     * @param globalName      the global category of where this defaultPostResponse is going
     * @param localCategory   the local category uid of where this defaultPostResponse is going
     * @param conversationUID the conversation uid of where this defaultPostResponse is going
     * @param responseUID     the response uid of where this defaultPostResponse is going
     * @param postInfo        all the information about about the defaultPostResponse
     * @return whether the creation was successful
     */
    public static boolean set(String folder, String globalName, int localCategory, int conversationUID, int responseUID,
                              ConvoRespPostInfo postInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER,
                File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection postConfig = config.getConfigurationSection(
                String.format("%d%s%s%s%d%s%s%s%d%c%s", localCategory, ".", YMLConversationNavigate.CONVERSATIONS, ".",
                        conversationUID, ".", YMLConversationNavigate.OPTIONS, ".", responseUID, '.',
                        YMLConversationNavigate.DEFAULT_POST_RESPONSE));
        if (postConfig == null)
            return false;
        Evaluateable redirectReq = new BooleanExpRequirement(true);
        CreateResponse.set(postConfig, postInfo, redirectReq);
        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}

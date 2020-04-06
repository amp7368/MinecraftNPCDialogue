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

public class CreatePostDefault {
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
        CreateResponse.create(postConfig, postInfo, redirectReq);
        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}

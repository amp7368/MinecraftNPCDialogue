package apple.npc.creation.from_scratch.convo.single;

import apple.npc.creation.from_scratch.convo.components.CreateBooleanExp;
import apple.npc.creation.from_scratch.convo.info.ConvoRespInfo;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateConvoResponse {
    /**
     * Create a player's base conversation response without post responses
     *
     * @param folder          the base folder in which the file resides (not including CONVERSATION_FOLDER)
     * @param globalName      the global category for which the conversation resides
     * @param localCategory   the local category for which the conversation resides
     * @param conversationUID the conversation for which the response resides
     * @param respInfo        the response information about the response
     * @return whether the creation was successful
     */
    public static boolean create(String folder, String globalName, int localCategory, int conversationUID, ConvoRespInfo respInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER,
                File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection respConfigOrig = config.getConfigurationSection(
                String.format("%d%s%s%s%d%s%s", localCategory, ".", YMLConversationNavigate.CONVERSATIONS, ".",
                        conversationUID, ".", YMLConversationNavigate.OPTIONS));
        if (respConfigOrig == null)
            return false;
        String responseUidString = String.valueOf(respInfo.uid);
        if (respConfigOrig.contains(responseUidString))
            return false;
        ConfigurationSection respConfig = respConfigOrig.createSection(responseUidString);
        respConfig.set(YMLConversationNavigate.RESPONSE_UID, respInfo.uid);
        ConfigurationSection preResponse = respConfig.createSection(YMLConversationNavigate.PRE_RESPONSE_REQUIREMENT);
        CreateBooleanExp.setBooleanExp(preResponse.createSection(YMLBooleanNavigate.EXPRESSION), new BooleanExpRequirement(true));
        respConfig.set(YMLConversationNavigate.RESPONSE_TEXT, respInfo.text);
        respConfig.createSection(YMLConversationNavigate.POST_RESPONSES);
        respConfig.createSection(YMLConversationNavigate.DEFAULT_POST_RESPONSE);

        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

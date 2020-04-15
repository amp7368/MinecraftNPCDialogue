package apple.npc.creation.from_data.convo;

import apple.npc.creation.from_data.bool.WriteBoolean;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.PostPlayerResponse;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class WriteConvoResponse {

    public static void setConfig(ConfigurationSection config, ConversationResponse response) {
        config.set(YMLConversationNavigate.RESPONSE_UID, response.uid);
        WriteBoolean.setConfig(config.createSection(YMLConversationNavigate.PRE_RESPONSE_REQUIREMENT).createSection(YMLBooleanNavigate.EXPRESSION), response.getPreResponseRequirement());
        config.set(YMLConversationNavigate.RESPONSE_TEXT, response.response);
        List<PostPlayerResponse> postResponses = response.getPostResponses();

        ConfigurationSection postResponseConfig = config.createSection(YMLConversationNavigate.POST_RESPONSES);
        config.set("YMLConversationNavigate.POST_RESPONSES", null); //todo make sure this works
        for (int i = 0; i < postResponses.size(); i++) {
            WriteConvoPostResponse.setConfig(postResponseConfig.createSection(String.valueOf(i)), postResponses.get(i));
        }
        WriteConvoPostResponse.setConfig(config.createSection(YMLConversationNavigate.DEFAULT_POST_RESPONSE),response.getDefaultPostReponse());
    }
}

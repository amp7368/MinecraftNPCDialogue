package apple.npc.creation.from_data.convo;

import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class WriteConvoConvo {
    public static void setConfig(ConfigurationSection config, ConversationData conversation) {
        config.set(YMLConversationNavigate.CONVERSATION_UID, conversation.uid);
        config.set(YMLConversationNavigate.NAME, conversation.name);
        config.set(YMLConversationNavigate.CONVERSATION_TEXT, conversation.conversationText);

        ConfigurationSection options = config.createSection(YMLConversationNavigate.OPTIONS);
        for (ConversationResponse response : conversation.responses) {
            WriteConvoResponse.setConfig(options.createSection(String.valueOf(response.uid)), response);
        }
        WriteConvoPostResponse.setConfig(config.createSection(YMLConversationNavigate.IMMEDIATE_CONVO), conversation.immediateConvo);

    }
}

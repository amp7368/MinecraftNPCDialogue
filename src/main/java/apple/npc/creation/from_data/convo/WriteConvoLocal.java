package apple.npc.creation.from_data.convo;

import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationLocalCategory;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class WriteConvoLocal {
    public static void setConfig(ConfigurationSection config, ConversationLocalCategory local) {
        config.set(YMLConversationNavigate.LOCAL_UID, local.getUid());
        config.set(YMLConversationNavigate.NAME, local.getName());
        ConfigurationSection conversationsConfig=config.createSection(YMLConversationNavigate.CONVERSATIONS);
        Map<Integer, ConversationData> conversationData = local.getConversations();
        for(Integer key:conversationData.keySet()){
            WriteConvoConvo.setConfig(conversationsConfig.createSection(String.valueOf(key)),conversationData.get(key));
        }
    }
}

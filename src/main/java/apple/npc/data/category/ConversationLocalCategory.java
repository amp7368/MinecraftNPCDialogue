package apple.npc.data.category;

import apple.npc.data.single.ConversationData;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConversationLocalCategory {
    public int uid;
    public String name;
    public Map<Integer, ConversationData> conversations;

    public ConversationLocalCategory(ConfigurationSection config) {
        conversations = new HashMap<>();
        uid = config.getInt(YMLConversationNavigate.LOCAL_UID);
        name = config.getString(YMLConversationNavigate.NAME);
        ConfigurationSection conversationsConfig = config.getConfigurationSection(YMLConversationNavigate.CONVERSATIONS);
        Set<String> conversationUIDs = conversationsConfig.getKeys(false);
        for (String conversation : conversationUIDs) {
            if (!StringUtils.isNumeric(conversation))
                continue;
            int conversationUID = Integer.parseInt(conversation);
            conversations.put(conversationUID, new ConversationData(conversationsConfig.getConfigurationSection(conversation)));
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(YMLConversationNavigate.LOCAL_UID + ": ");
        string.append(uid);
        string.append(" " + YMLConversationNavigate.NAME + ": ");
        string.append(name);
        string.append("\n"+YMLConversationNavigate.CONVERSATIONS+":\n");
        for (Integer key : conversations.keySet()) {
            string.append(YMLConversationNavigate.KEY+": ");
            string.append(key);
            string.append("\n");
            string.append(conversations.get(key).toString());
            string.append("\n");
        }
        string.append("\n");
        return string.toString();
    }

    public ConversationData get(int uid) {
        return conversations.get(uid);
    }
}

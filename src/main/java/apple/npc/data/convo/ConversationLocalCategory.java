package apple.npc.data.convo;

import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class ConversationLocalCategory {
    private int uid;
    private String name;
    private Map<Integer, ConversationData> conversations;

    public ConversationLocalCategory(ConfigurationSection config) {
        conversations = new HashMap<>();
        uid = config.getInt(YMLConversationNavigate.LOCAL_UID);
        name = config.getString(YMLConversationNavigate.NAME);
        ConfigurationSection conversationsConfig = config.getConfigurationSection(YMLConversationNavigate.CONVERSATIONS);
        if (conversationsConfig == null) {
            // todo show and error message
            config.createSection(YMLConversationNavigate.CONVERSATIONS);
            return;
        }
        Set<String> conversationUIDs = conversationsConfig.getKeys(false);
        for (String conversation : conversationUIDs) {
            int conversationUID;
            try {
                conversationUID = Integer.parseInt(conversation);
            } catch (NumberFormatException e) {
                System.err.println("not numeric");
                continue;
            }
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
        string.append("\n" + YMLConversationNavigate.CONVERSATIONS + ":\n");
        for (Integer key : conversations.keySet()) {
            string.append("key" + ": ");
            string.append(key);
            string.append("\n");
            string.append(conversations.get(key).toString());
            string.append("\n");
        }
        string.append("\n");
        return string.toString();
    }

    public ConversationData get(int uid) {
        return conversations.getOrDefault(uid, null);
    }

    public List<Integer> getConvoUIDs(String convoName) {
        List<Integer> convoUIDs = new ArrayList<>();
        for (ConversationData conversation : conversations.values()) {
            if (conversation.name.equals(convoName))
                convoUIDs.add(conversation.uid);
        }
        return convoUIDs;
    }

    public boolean equalsLocalName(String local) {
        return name.equals(local);
    }

    public boolean convoUIDExists(int convoUID) {
        for (ConversationData conversation : conversations.values()) {
            if (conversation.uid == convoUID)
                return true;
        }
        return false;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, ConversationData> getConversations() {
        return conversations;
    }
}

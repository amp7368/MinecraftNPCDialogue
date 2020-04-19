package apple.npc.data.convo;

import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class ConversationLocalCategory {
    private int uid;
    private String name;
    private Map<Integer, ConversationData> conversations;
    private Map<Integer, ConversationTagCollection> convoTags;

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
        convoTags = new HashMap<>();
    }

    public ConversationLocalCategory(int uid, String name) {
        conversations = new HashMap<>();
        this.uid = uid;
        this.name = name;
    }


    public ConversationTagCollection collectTags() {
        ConversationTagCollection tags = new ConversationTagCollection();
        for (Integer key : conversations.keySet()) {
            ConversationTagCollection tag = conversations.get(key).collectTags();
            convoTags.put(key, tag);
            tags.add(tag);
        }
        return tags;
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

    public void createConvo(String global, int local, String convo, List<String> text) {
        // iterate until you find an empty convo uid
        int convoUID = 0;
        while (conversations.containsKey(convoUID)) {
            convoUID++;
        }
        conversations.put(convoUID, new ConversationData(global, local, convoUID, convo, text));

    }

    public void createResponse(String global, int local, int convo, List<String> text) {
        if (conversations.containsKey(convo)) {
            conversations.get(convo).createResponse(global, local, convo, text);
        }
    }

    public void setRedirectRequirements(int convo, int responseUID, int redirectNum, Evaluateable exp) {
        if (conversations.containsKey(convo))
            conversations.get(convo).setRedirectRequirements(responseUID, redirectNum, exp);
    }
}

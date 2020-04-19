package apple.npc.data.convo;

import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class ConversationData {
    public int uid;
    public String name;
    public List<String> conversationText;
    public List<ConversationResponse> responses;
    public PostPlayerResponse immediateConvo;
    public ConversationTagCollection tags;

    public ConversationData(ConfigurationSection config) {
        conversationText = new ArrayList<>();
        this.uid = config.getInt(YMLConversationNavigate.LOCAL_UID);
        this.name = config.getString(YMLConversationNavigate.NAME);
        this.conversationText = config.getStringList(YMLConversationNavigate.CONVERSATION_TEXT);
        this.responses = getResponses(config.getConfigurationSection(YMLConversationNavigate.OPTIONS));
        this.immediateConvo = new PostPlayerResponse(config.getConfigurationSection(YMLConversationNavigate.IMMEDIATE_CONVO));
        this.tags = getTags(config.getConfigurationSection(YMLConversationNavigate.TAGS));
    }

    public ConversationData(String global, int local, int uid, String name, List<String> text) {
        this.uid = uid;
        this.name = name;
        this.conversationText = text;
        responses = new ArrayList<>();
        immediateConvo = new PostPlayerResponse(global, local, uid);
        tags = new ConversationTagCollection();
    }

    public ConversationTagCollection collectTags() {
        return tags;
    }

    private ConversationTagCollection getTags(ConfigurationSection config) {
        Set<ConversationTag> tags = new HashSet<>();
        Set<String> keys = config.getKeys(false);
        for (String key : keys) {
            tags.add(new ConversationTag(key));
        }
        return new ConversationTagCollection(tags);
    }

    private List<ConversationResponse> getResponses(ConfigurationSection config) {
        List<ConversationResponse> responses = new ArrayList<>();
        // just loop through 1 to _ because I don't know a better way to do it.
        int i = 0;
        ConfigurationSection optionConfig = config.getConfigurationSection(String.valueOf(i++));
        while (optionConfig != null) {
            responses.add(new ConversationResponse(optionConfig));
            optionConfig = config.getConfigurationSection(String.valueOf(i++));
        }
        return responses;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("uid: ");
        string.append(uid);
        string.append(", name: ");
        string.append(name);
        string.append(", conversation: ");
        string.append(conversationText.toString());
        string.append("\nresponses: \n");
        for (ConversationResponse response : responses) {
            string.append(response.toString());
            string.append("\n");
        }
        string.append("\n");
        return string.toString();
    }

    public boolean contains(int responseId) {
        for (ConversationResponse response : responses) {
            if (response.uid == responseId)
                return true;
        }
        return false;
    }

    public ConversationResponse get(int responseId) {
        for (ConversationResponse response : responses) {
            if (response.uid == responseId)
                return response;
        }
        return null;
    }

    public void createResponse(String global, int local, int convo, List<String> text) {
        int responseUID = 0;
        while (contains(responseUID)) {
            responseUID++;
        }
        responses.add(new ConversationResponse(global, local, convo, responseUID, text));
    }

    public void setRedirectRequirements(int responseUID, int redirectNum, Evaluateable exp) {
        if (responses.size() > responseUID)
            responses.get(responseUID).setRedirectRequirements(redirectNum, exp);
    }
}


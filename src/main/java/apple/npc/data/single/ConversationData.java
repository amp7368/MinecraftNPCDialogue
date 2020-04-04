package apple.npc.data.single;

import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class ConversationData {
    public int uid;
    public String name;
    public List<String> conversationText;
    public List<ConversationResponse> responses;

    public ConversationData(ConfigurationSection config) {
        conversationText = new ArrayList<>();
        this.uid = config.getInt("uid");
        this.name = config.getString("name");
        this.conversationText = config.getStringList("conversationText");
        this.responses = getResponses(config.getConfigurationSection("options"));
    }

    private List<ConversationResponse> getResponses(ConfigurationSection config) {
        List<ConversationResponse> responses = new ArrayList<>();
        // just loop through 1 to _ because I don't know a better way to do it.
        int i = 1;
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
}


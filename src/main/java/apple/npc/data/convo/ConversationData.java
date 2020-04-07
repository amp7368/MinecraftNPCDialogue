package apple.npc.data.convo;

import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class ConversationData {
    public int uid;
    public String name;
    public List<String> conversationText;
    public List<ConversationResponse> responses;

    public ConversationData(ConfigurationSection config) {
        conversationText = new ArrayList<>();
        this.uid = config.getInt(YMLConversationNavigate.LOCAL_UID);
        this.name = config.getString(YMLConversationNavigate.NAME);
        this.conversationText = config.getStringList(YMLConversationNavigate.CONVERSATION_TEXT);
        this.responses = getResponses(config.getConfigurationSection(YMLConversationNavigate.OPTIONS));
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

    public boolean contains(int responseId) {
        for (ConversationResponse response : responses) {
            if (response.uid == responseId)
                return true;
        }
        return false;
    }

    public static class PostPlayerResponse {
        private final HashSet<VariableChange> variableChanges;
        private final String responseGlobal;
        private final int responseLocal;
        private final int conversationUID;
        private final BooleanExpRequirement redirectRequirements;

        public PostPlayerResponse(ConfigurationSection config) {
            this.responseGlobal = config.getString(YMLConversationNavigate.RESPONSE_GLOBAL_CATEGORY);
            this.responseLocal = config.getInt(YMLConversationNavigate.RESPONSE_LOCAL_CATEGORY_UID);
            this.conversationUID = config.getInt(YMLConversationNavigate.RESPONSE_CONVERSATION_UID);
            this.redirectRequirements = new BooleanExpRequirement(config.getConfigurationSection(YMLConversationNavigate.REDIRECT_REQUIREMENT));

            this.variableChanges = new HashSet<>();
            ConfigurationSection changesConfig = config.getConfigurationSection(YMLConversationNavigate.VARIABLE_CHANGES);
            if (changesConfig == null) {
                return;
            }
            Set<String> changesKeys = changesConfig.getKeys(false);
            for (String changeKey : changesKeys) {
                this.variableChanges.add(new VariableChange(changesConfig.getConfigurationSection(changeKey)));
            }
        }

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();
            string.append("changes: \n");
            for (VariableChange change : variableChanges) {
                string.append(change.toString());
                string.append("\n");
            }
            string.append("responseGlobal: ");
            string.append(responseGlobal);
            string.append("\nresponseLocal");
            string.append(responseLocal);
            string.append("\nconversationUID: ");
            string.append(conversationUID);
            string.append("\n<boolean>");
            return string.toString();
        }
    }
}


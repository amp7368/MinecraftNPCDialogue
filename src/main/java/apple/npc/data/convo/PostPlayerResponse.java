package apple.npc.data.convo;

import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Set;

public class PostPlayerResponse {
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

    public ConvoID toNpcConvoID() {
        return new ConvoID(responseGlobal,responseLocal,conversationUID);
    }

    public boolean evaluate(String playerUID,int currentOpinion,long timeLastTalked) {
        return redirectRequirements.evaluate(playerUID,currentOpinion,timeLastTalked);
    }
}

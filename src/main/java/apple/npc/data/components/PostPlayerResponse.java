package apple.npc.data.components;

import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Set;

public class PostPlayerResponse {
    private final HashSet<VariableChange> variableChanges;
    private final String responseGlobal;
    private final String responseLocal;
    private final String conversationUID;
    private final BooleanExpRequirement redirectRequirements;

    public PostPlayerResponse(ConfigurationSection config) {
        this.responseGlobal = config.getString("globalCategory");
        this.responseLocal = config.getString("localCategoryUID");
        this.conversationUID = config.getString("conversationUID");
        this.redirectRequirements = new BooleanExpRequirement(config.getConfigurationSection("redirectRequirement"));

        this.variableChanges = new HashSet<>();
        ConfigurationSection changesConfig = config.getConfigurationSection("variableChanges");
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

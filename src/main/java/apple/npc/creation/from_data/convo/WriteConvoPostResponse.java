package apple.npc.creation.from_data.convo;

import apple.npc.creation.from_data.bool.WriteBoolean;
import apple.npc.data.convo.PostPlayerResponse;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class WriteConvoPostResponse {
    public static void setConfig(ConfigurationSection config, PostPlayerResponse postResponse) {
        config.set(YMLConversationNavigate.RESPONSE_GLOBAL_CATEGORY, postResponse.getResponseGlobal());
        config.set(YMLConversationNavigate.RESPONSE_LOCAL_CATEGORY_UID, postResponse.getResponseLocal());
        config.set(YMLConversationNavigate.RESPONSE_CONVERSATION_UID, postResponse.getConversationUID());

        WriteBoolean.setConfig(config.createSection(YMLConversationNavigate.REDIRECT_REQUIREMENT), postResponse.getRedirectRequirements());
        ConfigurationSection subConfig = config.createSection(YMLConversationNavigate.VARIABLE_CHANGES);
        config.set(YMLConversationNavigate.VARIABLE_CHANGES, null);
        WriteConvoVariableChanges.setConfig(subConfig, postResponse.getVariableChanges());
    }
}

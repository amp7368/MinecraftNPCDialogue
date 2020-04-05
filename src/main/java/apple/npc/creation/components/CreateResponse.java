package apple.npc.creation.components;

import apple.npc.creation.info.ConvoRespPostInfo;
import apple.npc.creation.info.VariableChangeInfo;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class CreateResponse {
    public static void create(ConfigurationSection postConfig, ConvoRespPostInfo postInfo, Evaluateable redirectReq) {
        postConfig.set(YMLConversationNavigate.RESPONSE_GLOBAL_CATEGORY, postInfo.globalCategory);
        postConfig.set(YMLConversationNavigate.RESPONSE_LOCAL_CATEGORY_UID, postInfo.localCategoryUID);
        postConfig.set(YMLConversationNavigate.RESPONSE_CONVERSATION_UID, postInfo.conversationUID);
        ConfigurationSection redirectReqConfig = postConfig.createSection(YMLConversationNavigate.REDIRECT_REQUIREMENT);
        CreateBooleanExp.setBooleanExp(redirectReqConfig, redirectReq);
        ConfigurationSection variableChangeConfig = postConfig.createSection(YMLConversationNavigate.VARIABLE_CHANGES);
        for (VariableChangeInfo variableChange : postInfo.variableChanges) {
            ConfigurationSection variableSection = variableChangeConfig.createSection(String.valueOf(variableChange.key));
            variableSection.set(YMLConversationNavigate.VARIABLE_CHANGE_GLOBAL, variableChange.global);
            variableSection.set(YMLConversationNavigate.VARIABLE_CHANGE_VAR_UID, variableChange.varUid);
            variableSection.set(YMLConversationNavigate.VARIABLE_CHANGE_NEW_VALUE, variableChange.newValue);
        }
    }
}

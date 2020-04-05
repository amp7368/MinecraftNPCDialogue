package apple.npc.creation.components;

import apple.npc.creation.info.ConvoRespPostInfo;
import apple.npc.creation.info.VariableChangeInfo;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreatePostRequirements {
    public static boolean create(String folder, String globalName, int localCategory, int conversationUID,
                                 int responseUID, ConvoRespPostInfo postInfo, Evaluateable redirectReqInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER,
                File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection postConfigOrig = config.getConfigurationSection(
                String.format("%d%s%s%s%d%s%s%s%d%c%s", localCategory, ".", YMLConversationNavigate.CONVERSATIONS, ".",
                        conversationUID, ".", YMLConversationNavigate.OPTIONS, ".", responseUID, '.', YMLConversationNavigate.POST_RESPONSES));
        if (postConfigOrig.contains(postInfo.responseId)) {
            return false;
        }
        postConfigOrig.createSection(postInfo.responseId);
        ConfigurationSection postConfig = postConfigOrig.getConfigurationSection(postInfo.responseId);
        postConfig.set(YMLConversationNavigate.RESPONSE_GLOBAL_CATEGORY, postInfo.globalCategory);
        postConfig.set(YMLConversationNavigate.RESPONSE_LOCAL_CATEGORY_UID, postInfo.localCategoryUID);
        postConfig.set(YMLConversationNavigate.RESPONSE_CONVERSATION_UID, postInfo.conversationUID);
        ConfigurationSection redirectReqConfig = postConfig.createSection(YMLConversationNavigate.REDIRECT_REQUIREMENT);
        CreateBooleanExp.setBooleanExp(redirectReqConfig, redirectReqInfo);
        ConfigurationSection variableChangeConfig = postConfig.createSection(YMLConversationNavigate.VARIABLE_CHANGES);
        for (VariableChangeInfo variableChange : postInfo.variableChanges) {
            ConfigurationSection variableSection = variableChangeConfig.createSection(String.valueOf(variableChange.key));
            variableSection.set(YMLConversationNavigate.VARIABLE_CHANGE_GLOBAL, variableChange.global);
            variableSection.set(YMLConversationNavigate.VARIABLE_CHANGE_VAR_UID, variableChange.varUid);
            variableSection.set(YMLConversationNavigate.VARIABLE_CHANGE_NEW_VALUE, variableChange.newValue);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

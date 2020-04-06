package apple.npc.creation.convo.components;

import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PreResponseRequirements {
    public static boolean set(String folder, String globalName, int localCategory, int conversationUID,
                              int responseUID, Evaluateable reqInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER,
                File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection requireConfigOrig = config.getConfigurationSection(
                String.format("%d%s%s%s%d%s%s%s%d%c%s", localCategory, ".", YMLConversationNavigate.CONVERSATIONS, ".",
                        conversationUID, ".", YMLConversationNavigate.OPTIONS, ".", responseUID, '.', YMLConversationNavigate.PRE_RESPONSE_REQUIREMENT));
        if (requireConfigOrig == null)
            return false;
        ConfigurationSection requireConfig = requireConfigOrig.createSection(YMLBooleanNavigate.EXPRESSION);
        CreateBooleanExp.setBooleanExp(requireConfig, reqInfo);
        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

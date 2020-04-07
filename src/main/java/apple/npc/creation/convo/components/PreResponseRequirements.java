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
    /**
     * sets the file for the preResponseRequirements for a particular dialogue option for a player to respond with
     *
     * @param folder          the base folder where we're writing to (not including the CONVERSATION_FOLDER)
     * @param globalName      the global category for the conversation
     * @param localCategory   the local category for the conversation
     * @param conversationUID the conversation uid where the response resides
     * @param responseUID     the particular player response we're refering to
     * @param reqInfo         the boolean expression referring to the prerequisites for the player to choose this dialogue option
     * @return whether the setting of the file was successful
     */
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

package apple.npc.creation.from_data.convo;

import apple.npc.data.convo.VariableChange;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;

public class WriteConvoVariableChanges {

    public static void setConfig(ConfigurationSection config, HashSet<VariableChange> variableChanges) {
        int i = 0;
        for (VariableChange variableChange : variableChanges) {
            ConfigurationSection variableConfig = config.createSection(String.valueOf(i++));
            variableConfig.set(YMLConversationNavigate.VARIABLE_CHANGE_GLOBAL, variableChange.globalVar);
            variableConfig.set(YMLConversationNavigate.VARIABLE_CHANGE_VAR_UID, variableChange.varUID);
            variableConfig.set(YMLConversationNavigate.VARIABLE_CHANGE_NEW_VALUE, variableChange.newValue);
        }
    }
}

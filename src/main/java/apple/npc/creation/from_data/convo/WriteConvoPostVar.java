package apple.npc.creation.from_data.convo;

import apple.npc.data.convo.VariableChange;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;

public class WriteConvoPostVar {
    public static void setConfig(ConfigurationSection config, HashSet<VariableChange> variableChanges) {
        int i = 0;
        for (VariableChange varChange : variableChanges) {
            ConfigurationSection subConfig = config.createSection(String.valueOf(i++));
            subConfig.set(YMLConversationNavigate.VARIABLE_CHANGE_GLOBAL,varChange.globalVar);
            subConfig.set(YMLConversationNavigate.VARIABLE_CHANGE_VAR_UID,varChange.varUID);
            subConfig.set(YMLConversationNavigate.VARIABLE_CHANGE_VAR_NAME,varChange.varName);
            subConfig.set(YMLConversationNavigate.VARIABLE_CHANGE_NEW_VALUE,varChange.newValue);
        }
    }
}

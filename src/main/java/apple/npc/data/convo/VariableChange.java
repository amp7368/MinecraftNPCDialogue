package apple.npc.data.convo;

import apple.npc.data.player.Variable;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

public class VariableChange {
    public final String globalVar;
    public final int varUID;
    public final int newValue;
    public final String varName;

    public VariableChange(ConfigurationSection config) {
        this.globalVar = config.getString(YMLConversationNavigate.VARIABLE_CHANGE_GLOBAL);
        this.varUID = config.getInt(YMLConversationNavigate.VARIABLE_CHANGE_VAR_UID);
        this.newValue = config.getInt(YMLConversationNavigate.VARIABLE_CHANGE_NEW_VALUE);
        this.varName = config.getString(YMLConversationNavigate.VARIABLE_CHANGE_VAR_NAME);
    }

    public VariableChange(String respGlobal, Variable variable, int respVal) {
        globalVar = respGlobal;
        varUID = variable.uid;
        newValue = respVal;
        varName = variable.name;
    }

    @Override
    public String toString() {
        return "{globalVar: " + globalVar + ", varUID: " + varUID + ", newValue: " + newValue + "}";
    }
}

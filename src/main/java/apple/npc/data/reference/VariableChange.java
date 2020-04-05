package apple.npc.data.reference;

import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

public class VariableChange {
    public final String globalVar;
    public final int varUID;
    public final int newValue;

    public VariableChange(ConfigurationSection config) {
        this.globalVar = config.getString(YMLPlayerVariable.VARIABLE_CHANGE_GLOBAL);
        this.varUID = config.getInt(YMLPlayerVariable.VARIABLE_CHANGE_VAR_UID);
        this.newValue = config.getInt(YMLPlayerVariable.VARIABLE_CHANGE_NEW_VALUE);
    }

    @Override
    public String toString() {
        return "{globalVar: " + globalVar + ", varUID: " + varUID + ", newValue: " + newValue + "}";
    }
}

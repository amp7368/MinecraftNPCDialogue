package apple.npc.data.reference;

import org.bukkit.configuration.ConfigurationSection;

public class VariableChange {
    public final String globalVar;
    public final int varUID;
    public final int newValue;

    public VariableChange(ConfigurationSection config) {
        this.globalVar = config.getString("globalCategory");
        this.varUID = config.getInt("varUID");
        this.newValue = config.getInt("newValue");
    }

    @Override
    public String toString() {
        return "{globalVar: " + globalVar + ", varUID: " + varUID + ", newValue: " + newValue + "}";
    }
}

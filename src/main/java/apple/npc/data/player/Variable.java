package apple.npc.data.player;

import apple.npc.data.convo.VariableChange;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

public class Variable {
    public int uid;
    public String name;
    public int value;

    public Variable(ConfigurationSection config) {
        uid = config.getInt(YMLPlayerVariable.VARIABLE_UID);
        name = config.getString(YMLPlayerVariable.VARIABLE_NAME);
        value = config.getInt(YMLPlayerVariable.VARIABLE_VALUE);
    }

    public Variable(int uid, String name) {
        this.uid = uid;
        this.name = name;
        this.value = -1;
    }

    public Variable(VariableChange variable) {
        this.uid = variable.varUID;
        this.name = variable.varName;
        this.value = variable.newValue;
    }

    public String toString() {
        return String.format("uid: %d, name: %s, value: %d", uid, name, value);
    }
}

package apple.npc.data.components;

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

    public String toString() {
        return String.format("uid: %d, name: %s, value: %d", uid, name, value);
    }
}

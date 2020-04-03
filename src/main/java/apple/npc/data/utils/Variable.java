package apple.npc.data.utils;

import org.bukkit.configuration.ConfigurationSection;

public class Variable {
    public int uid;
    public String name;
    public int value;

    public Variable(ConfigurationSection config) {
        uid = config.getInt("uid");
        name = config.getString("name");
        value = config.getInt("value");
    }

    public String toString() {
        return String.format("uid: %d, name: %s, value: %d", uid, name, value);
    }
}

package apple.npc.data.single;

import org.bukkit.configuration.ConfigurationSection;

public class ConvoID {
    public String global;
    public int local;
    public int uid;

    public ConvoID(ConfigurationSection config) {
        global = config.getString("convoGlobalId");
        local = config.getInt("convoLocalId");
        uid = config.getInt("convoUID");
    }
}

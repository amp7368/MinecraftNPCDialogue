package apple.npc.data.components;

import org.bukkit.configuration.ConfigurationSection;

public class Opinion {
    public int opinionUID;
    public String opinionName;

    public Opinion(ConfigurationSection config) {
        opinionUID = config.getInt("opinionNum");
        opinionName = config.getString("opinionName");
    }
}

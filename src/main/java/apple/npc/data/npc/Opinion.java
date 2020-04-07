package apple.npc.data.npc;

import org.bukkit.configuration.ConfigurationSection;

public class Opinion {
    public int opinionUID;
    public String opinionName;

    public Opinion(ConfigurationSection config) {
        opinionUID = config.getInt("opinionNum");
        opinionName = config.getString("opinionName");
    }

    public Opinion(int currentOpinion, String name) {
        this.opinionUID = currentOpinion;
        this.opinionName = name;
    }
}

package apple.npc.data.npc;

import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

public class Opinion {
    public int opinionUID;
    public String opinionName;

    public Opinion(ConfigurationSection config) {
        opinionUID = config.getInt(YMLPlayerVariable.OPINION_NUM);
        opinionName = config.getString(YMLPlayerVariable.OPINION_NAME);
    }

    public Opinion(int currentOpinion, String name) {
        this.opinionUID = currentOpinion;
        this.opinionName = name;
    }
}

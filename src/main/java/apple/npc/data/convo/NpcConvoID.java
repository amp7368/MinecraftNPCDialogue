package apple.npc.data.convo;

import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class NpcConvoID {
    public String global;
    public int local;
    public int uid;

    public NpcConvoID(ConfigurationSection config) {
        global = config.getString(YMLNpcNavigate.CONVO_GLOBAL);
        local = config.getInt(YMLNpcNavigate.CONVO_LOCAL);
        uid = config.getInt(YMLNpcNavigate.CONVO_UID);
    }

    public NpcConvoID(String global, int local, int uid) {
        this.global = global;
        this.local = local;
        this.uid = uid;
    }
}

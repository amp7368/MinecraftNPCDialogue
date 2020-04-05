package apple.npc.data.reference;

import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class ConvoID {
    public String global;
    public int local;
    public int uid;

    public ConvoID(ConfigurationSection config) {
        global = config.getString(YMLConversationNavigate.CONVERSATION_GLOBAL_ID);
        local = config.getInt(YMLConversationNavigate.CONVERSATION_LOCAL_ID);
        uid = config.getInt(YMLConversationNavigate.CONVERSATION_UID_ID);
    }
}

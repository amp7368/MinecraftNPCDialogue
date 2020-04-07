package apple.npc.data.npc;

import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

public class NPCPlayerData {
    public String playerUID;
    public int currentConvoUID;
    public long lastTalked; // System.currentTimeMillis()
    public Opinion opinion;

    public NPCPlayerData(ConfigurationSection config) {
        playerUID = config.getString(YMLPlayerVariable.PLAYER_UID);
        currentConvoUID = config.getInt(YMLPlayerVariable.CURRENT_CONVERSATION_ID);
        lastTalked = config.getLong(YMLPlayerVariable.TIME_LAST_TALKED);
        opinion = new Opinion(config.getConfigurationSection(YMLPlayerVariable.OPINION));
    }
}
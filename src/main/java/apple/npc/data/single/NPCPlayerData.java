package apple.npc.data.single;

import apple.npc.data.components.Opinion;
import org.bukkit.configuration.ConfigurationSection;

public class NPCPlayerData {
    public String playerUID;
    public int currentConvoUID;
    public long lastTalked; // System.currentTimeMillis()
    public Opinion opinion;

    public NPCPlayerData(ConfigurationSection config) {
        playerUID = config.getString("uid");
        currentConvoUID = config.getInt("currentConvoUID");
        lastTalked = config.getLong("timeLastTalked");
        opinion = new Opinion(config.getConfigurationSection("opinion"));
    }
}
package apple.npc.data.single;

import apple.npc.data.utils.Opinion;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Timestamp;

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

name:bob # playerSetName(not unique)
        uid:1 # personal uid
        gameUid:d2fe30f4-64a7-4e27-8aeb-e229a5789e99
        # entity uid
        varsToConclusions:  # map of simplified booleans(int comparisons)to conclusions
        else:0
        conclusionsToConvoUid:  # map of conclusions to conversations
        0:convoId
        else:convoId
        playersuid:
        # player uid
        # -current conversation uid
        # -timeOfLastTalk(meant for if a player walked away)
        # -current opinion(keep because playerDataGlobalVariables will become cluttered)

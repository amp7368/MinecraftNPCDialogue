package apple.npc.data.npc;

import apple.npc.data.convo.NpcConvoID;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

public class NPCPlayerData {
    public String playerUID;
    public NpcConvoID currentConvoUID;
    public long lastTalked; // System.currentTimeMillis()
    public Opinion opinion;

    public NPCPlayerData(ConfigurationSection config) {
        playerUID = config.getString(YMLPlayerVariable.PLAYER_UID);
        currentConvoUID = new NpcConvoID(config.getConfigurationSection(YMLPlayerVariable.CURRENT_CONVERSATION_ID));
        lastTalked = config.getLong(YMLPlayerVariable.TIME_LAST_TALKED);
        opinion = new Opinion(config.getConfigurationSection(YMLPlayerVariable.OPINION));
    }

    public NPCPlayerData(String playerUID, NpcConvoID conversation, long lastTalked, Opinion opinion) {
        this.playerUID = playerUID;
        this.currentConvoUID = conversation;
        this.lastTalked = lastTalked;
        this.opinion = opinion;
    }

    public void setOpinion(int conclusionResult) {
        opinion.opinionUID = conclusionResult;
    }
}
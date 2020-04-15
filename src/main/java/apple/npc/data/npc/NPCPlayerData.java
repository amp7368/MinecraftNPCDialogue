package apple.npc.data.npc;

import apple.npc.data.convo.ConvoID;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

public class NPCPlayerData {
    public String playerUID;
    public ConvoID currentConvoUID;
    public long lastTalked; // System.currentTimeMillis()
    public Opinion opinion;

    public NPCPlayerData(ConfigurationSection config) {
        playerUID = config.getString(YMLPlayerVariable.PLAYER_UID);
        currentConvoUID = new ConvoID(config.getConfigurationSection(YMLPlayerVariable.CURRENT_CONVERSATION_ID));
        lastTalked = config.getLong(YMLPlayerVariable.TIME_LAST_TALKED);
        opinion = new Opinion(config.getConfigurationSection(YMLPlayerVariable.OPINION));
    }

    public NPCPlayerData(String playerUID, ConvoID conversation, long lastTalked, Opinion opinion) {
        this.playerUID = playerUID;
        this.currentConvoUID = conversation;
        this.lastTalked = lastTalked;
        this.opinion = opinion;
    }

    public void setOpinion(int conclusionResult) {
        opinion.opinionUID = conclusionResult;
    }
}
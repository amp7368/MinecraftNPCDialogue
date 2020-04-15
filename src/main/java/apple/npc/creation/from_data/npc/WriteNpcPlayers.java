package apple.npc.creation.from_data.npc;

import apple.npc.data.npc.NPCPlayerData;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class WriteNpcPlayers {
    public static void write(ConfigurationSection config, Map<String, NPCPlayerData> playerDataMap) {
        for (String playerUID : playerDataMap.keySet()) {
            ConfigurationSection playerConfig = config.createSection(playerUID);
            playerConfig.set(YMLPlayerVariable.PLAYER_UID, playerUID);

            NPCPlayerData playerData = playerDataMap.get(playerUID);
            ConfigurationSection subConfig = playerConfig.createSection(YMLPlayerVariable.CURRENT_CONVERSATION_ID);
            subConfig.set(YMLNpcNavigate.CONVO_GLOBAL, playerData.currentConvoUID.global);
            subConfig.set(YMLNpcNavigate.CONVO_LOCAL, playerData.currentConvoUID.local);
            subConfig.set(YMLNpcNavigate.CONVO_UID, playerData.currentConvoUID.uid);

            playerConfig.set(YMLPlayerVariable.TIME_LAST_TALKED, playerData.lastTalked);

            subConfig = playerConfig.createSection(YMLPlayerVariable.OPINION);
            subConfig.set(YMLPlayerVariable.OPINION_NUM, playerData.opinion.opinionUID);
            subConfig.set(YMLPlayerVariable.OPINION_NAME, playerData.opinion.opinionName);
        }
    }
}

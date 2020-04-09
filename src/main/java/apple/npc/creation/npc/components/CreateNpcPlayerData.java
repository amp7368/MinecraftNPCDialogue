package apple.npc.creation.npc.components;

import apple.npc.creation.npc.info.NpcPlayerDataInfo;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateNpcPlayerData {
    public static boolean set(String folder, String npcUID, String npcName, NpcPlayerDataInfo dataInfo) {
        File file = new File(String.format("%s%s%s%c%s%s", folder, File.separator, npcUID, ',', npcName, YMLFileNavigate.YML));
        YamlConfiguration configOrig = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection config = configOrig.getConfigurationSection(YMLNpcNavigate.PLAYERS_UID);
        if (config == null) {
            return false;
        }
        if (config.getKeys(false).contains(dataInfo.playerUID)) {
            config = config.getConfigurationSection(dataInfo.playerUID);
        } else {
            config = config.createSection(dataInfo.playerUID);
        }

        config.set(YMLPlayerVariable.PLAYER_UID, dataInfo.playerUID);
        ConfigurationSection subConfig = config.createSection(YMLPlayerVariable.CURRENT_CONVERSATION_ID);
        subConfig.set(YMLNpcNavigate.CONVO_GLOBAL, dataInfo.conversation.global);
        subConfig.set(YMLNpcNavigate.CONVO_LOCAL, dataInfo.conversation.local);
        subConfig.set(YMLNpcNavigate.CONVO_UID, dataInfo.conversation.uid);

        config.set(YMLPlayerVariable.TIME_LAST_TALKED, System.currentTimeMillis());

        subConfig = config.createSection(YMLPlayerVariable.OPINION);
        subConfig.set(YMLPlayerVariable.OPINION_NUM, dataInfo.currentOpinion);
        subConfig.set(YMLPlayerVariable.OPINION_NAME, dataInfo.opinionName);

        try {
            configOrig.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

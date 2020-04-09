package apple.npc.creation.from_data.npc;

import apple.npc.creation.from_data.convo.WriteConvoAll;
import apple.npc.data.convo.ConvoID;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class WriteNpcConcluToConvo {
    public static void setConfig(ConfigurationSection config, Map<Integer, ConvoID> conclusionsToConvo) {
        for (Integer key : conclusionsToConvo.keySet()) {
            ConfigurationSection subConfig = config.createSection(String.valueOf(key));
            ConvoID convo = conclusionsToConvo.get(key);
            subConfig.set(YMLNpcNavigate.CONVO_GLOBAL, convo.global);
            subConfig.set(YMLNpcNavigate.CONVO_LOCAL, convo.local);
            subConfig.set(YMLNpcNavigate.CONVO_UID, convo.uid);
        }
    }
}

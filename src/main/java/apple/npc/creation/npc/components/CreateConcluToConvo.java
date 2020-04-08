package apple.npc.creation.npc.components;

import apple.npc.creation.npc.info.ConcluToConvoInfo;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateConcluToConvo {
    /**
     * sets a single conclusion to conversation
     *
     * @param folder            the base folder for which the file resides (not including NPC_FOLDER)
     * @param personalUID       the uid for the npc
     * @param globalName        the global name for the npc
     * @param concluToConvoInfo the conclusionToConvo info including the conclusion and the conversation being redirected to
     * @return whether the setting was successful
     */
    public static boolean set(String folder, String personalUID, String globalName, ConcluToConvoInfo concluToConvoInfo) {
        File file = new File(String.format("%s%s%s%s%s%c%s%s", folder, File.separator, YMLFileNavigate.NPC_FOLDER, File.separator,
                personalUID, ',', globalName, YMLFileNavigate.YML));
        YamlConfiguration configFile = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configOrig = configFile.getConfigurationSection(YMLNpcNavigate.CONCLUSIONS_TO_CONVO);
        ConfigurationSection config;
        if (configOrig == null)
            return false;
        if (configOrig.contains(String.valueOf(concluToConvoInfo.key))) {
            config = configOrig.getConfigurationSection(String.valueOf(concluToConvoInfo.key));
        } else {
            config = configOrig.createSection(String.valueOf(concluToConvoInfo.key));
        }
        if (config == null)
            return false;
        config.set(YMLNpcNavigate.CONVO_GLOBAL, concluToConvoInfo.convoId.global);
        config.set(YMLNpcNavigate.CONVO_LOCAL, concluToConvoInfo.convoId.local);
        config.set(YMLNpcNavigate.CONVO_UID, concluToConvoInfo.convoId.uid);
        try {
            configFile.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

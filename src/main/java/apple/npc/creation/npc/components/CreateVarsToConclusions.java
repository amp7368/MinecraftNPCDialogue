package apple.npc.creation.npc.components;

import apple.npc.creation.convo.components.CreateBooleanExp;
import apple.npc.creation.npc.info.VarConcluInfo;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateVarsToConclusions {
    public static boolean set(String folder, int personalUID, String globalName, VarConcluInfo varConcluInfo) {
        File file = new File(String.format("%s%s%s%s%s%c%s%s", folder, File.separator, YMLFileNavigate.NPC_FOLDER, File.separator,
                personalUID, ',', globalName, YMLFileNavigate.YML));
        YamlConfiguration configFile = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configOrig = configFile.getConfigurationSection(YMLNpcNavigate.VARS_TO_CONCLUSIONS);
        if (configOrig == null)
            return false;
        ConfigurationSection config;
        if (configOrig.contains(varConcluInfo.key)) {
            config = configOrig.getConfigurationSection(varConcluInfo.key);
        } else {
            config = configOrig.createSection(varConcluInfo.key);
        }
        if (config == null)
            return false;
        config.set(YMLNpcNavigate.CONCLUSION_ID, varConcluInfo.conclusion);
        CreateBooleanExp.setBooleanExp(config.createSection(YMLBooleanNavigate.EXPRESSION), varConcluInfo.exp);

        try {
            configFile.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

package apple.npc.creation.npc;

import apple.npc.creation.npc.components.CreateVarsToConclusions;
import apple.npc.creation.npc.info.NpcInfo;
import apple.npc.creation.npc.info.VarConcluInfo;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLTestBoolean;
import apple.npc.ymlNavigate.YMLTestNpc;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CreateVarsToConcluTest {

    public boolean createTest(String folder) {
        String globalName = YMLTestNpc.GLOBAL_NAME;
        File file = new File(folder + File.separator + YMLTestBoolean.BOOLEAN_EXP_LOC_1);
        ConfigurationSection config = YamlConfiguration.loadConfiguration(file).getConfigurationSection(YMLBooleanNavigate.EXPRESSION);
        Evaluateable booleanExp = new BooleanExpRequirement(config);
        VarConcluInfo varConcluInfo = new VarConcluInfo(YMLTestNpc.VARS_KEY, YMLTestNpc.CONCLUSION_ID, booleanExp);
        return CreateVarsToConclusions.set(folder, YMLTestNpc.PERSONAL_UID, globalName, varConcluInfo);
    }
}

package apple.npc.creation;

import apple.npc.creation.components.PreResponseRequirements;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLTestBoolean;
import apple.npc.ymlNavigate.YMLTestConvo;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CreateConvoPreReqTest {

    public boolean createTest(String folder) {
        File file = new File(folder + File.separator + YMLTestBoolean.BOOLEAN_EXP_LOC_1);
        ConfigurationSection config = YamlConfiguration.loadConfiguration(file).getConfigurationSection(YMLBooleanNavigate.EXPRESSION);
        Evaluateable booleanExp = new BooleanExpRequirement(config);
        return PreResponseRequirements.set(folder, YMLTestConvo.CONVO_GLOBAL, YMLTestConvo.CONVO_LOCAL_UID,
                YMLTestConvo.CONVO_DATA_UID, YMLTestConvo.CONVO_RESPONSE_UID, booleanExp);
    }
}

package apple.npc.creation;

import apple.npc.creation.components.CreatePostRequirements;
import apple.npc.creation.info.ConvoRespPostInfo;
import apple.npc.creation.info.VariableChangeInfo;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLTestBoolean;
import apple.npc.ymlNavigate.YMLTestConvo;
import apple.npc.ymlNavigate.YMLTestPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateConvoPostReqTest {
    public boolean createTest(String folder) {
        String globalName = YMLTestConvo.CONVO_GLOBAL;
        int localCategory = YMLTestConvo.CONVO_LOCAL_UID;
        int conversationUID = YMLTestConvo.CONVO_DATA_UID;
        int responseUID = YMLTestConvo.CONVO_RESPONSE_UID;

        List<VariableChangeInfo> variableChanges = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            variableChanges.add(new VariableChangeInfo(YMLTestPlayer.CONVO_VAR_KEY, YMLTestPlayer.CONVO_VAR_GLOBAL, YMLTestPlayer.CONVO_VAR_UID, YMLTestPlayer.CONVO_VAR_VAL));
        }
        ConvoRespPostInfo postInfo = new ConvoRespPostInfo(responseUID, globalName, localCategory, conversationUID, variableChanges);

        File file = new File(folder + File.separator + YMLTestBoolean.BOOLEAN_EXP_LOC_1);
        ConfigurationSection config = YamlConfiguration.loadConfiguration(file).getConfigurationSection(YMLBooleanNavigate.EXPRESSION);
        Evaluateable redirectReqInfo = new BooleanExpRequirement(config);
        return CreatePostRequirements.create(folder, globalName, localCategory, conversationUID, responseUID, postInfo, redirectReqInfo);
    }
}

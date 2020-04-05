package apple.npc.creation.components;

import apple.npc.data.booleanAlgebra.BooleanExp;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.booleanAlgebra.VariableComparision;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PreResponseRequirements {
    public static boolean set(String folder, String globalName, int localCategory, int conversationUID,
                              int responseUID, Evaluateable reqInfo) {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER,
                File.separator, globalName, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection requireConfigOrig = config.getConfigurationSection(
                String.format("%d%s%s%s%d%s%s%s%d%c%s", localCategory, ".", YMLConversationNavigate.CONVERSATIONS, ".",
                        conversationUID, ".", YMLConversationNavigate.OPTIONS, ".", responseUID, '.', YMLConversationNavigate.PRE_RESPONSE_REQUIREMENT));
        ConfigurationSection requireConfig = requireConfigOrig.createSection(YMLBooleanNavigate.EXPRESSION);
        setBooleanExp(requireConfig, reqInfo);
        try {
            config.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static void setBooleanExp(ConfigurationSection config, Evaluateable exp) {
        if (exp == null) {
            return;
        }
        if (exp instanceof BooleanExp) {
            BooleanExp e = (BooleanExp) exp;
            config.set(YMLBooleanNavigate.IS_NOTED, e.isNot());
            config.set(YMLBooleanNavigate.IS_AND_OP, e.isAndOp());
            config.createSection(YMLBooleanNavigate.EXPRESSION_1);
            setBooleanExp(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_1), e.getExp1());
            config.createSection(YMLBooleanNavigate.EXPRESSION_2);
            setBooleanExp(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_2), e.getExp2());
        } else if (exp instanceof VariableComparision) {
            VariableComparision e = (VariableComparision) exp;
            config.set(YMLBooleanNavigate.IS_NOTED, e.isNot());
            config.set(YMLBooleanNavigate.IS_CONCLUSION_VAR, e.isConclusionVar());
            config.set(YMLBooleanNavigate.COMPARISON_TYPE, e.getComparisonType());
            config.set(YMLBooleanNavigate.COMPARISON_VALUE, e.getComparisonValue());
            config.createSection(YMLBooleanNavigate.COMPARISON_VAR);
            ConfigurationSection config2 = config.getConfigurationSection(YMLBooleanNavigate.COMPARISON_VAR);
            config2.set(YMLBooleanNavigate.GLOBAL_CATEGORY, e.getComparisonVarGlobal());
            config2.set(YMLBooleanNavigate.VAR_UID, e.getComparisonVarUID());
            int a = 4;
        } else if (exp instanceof BooleanExpRequirement) {
            BooleanExpRequirement e = (BooleanExpRequirement) exp;
            if (e.isDefault())
                config.set(YMLBooleanNavigate.DEFAULT, e.isDefaultVal());
            else {
                setBooleanExp(config, e.getExp());
            }
        }
    }
}

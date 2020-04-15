package apple.npc.creation.from_scratch.convo.components;

import apple.npc.data.booleanAlgebra.BooleanDoubleExp;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.booleanAlgebra.VariableComparision;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class CreateBooleanExp {
    /**
     * *sets* a config to follow the given expression (used to update a config file and then update the model)
     *
     * @param config the configuration section we're editing to follow the expression
     * @param exp    the expression we're following
     */
    public static void setBooleanExp(ConfigurationSection config, Evaluateable exp) {
        if (exp == null) {
            return;
        }
        if (exp instanceof BooleanDoubleExp) {
            BooleanDoubleExp e = (BooleanDoubleExp) exp;
            config.set(YMLBooleanNavigate.IS_NOTED, e.isNot());
            config.set(YMLBooleanNavigate.IS_AND_OP, e.isAndOp());
            config.createSection(YMLBooleanNavigate.EXPRESSION_1);
            setBooleanExp(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_1), e.getExp1());
            config.createSection(YMLBooleanNavigate.EXPRESSION_2);
            setBooleanExp(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_2), e.getExp2());
        } else if (exp instanceof VariableComparision) {
            VariableComparision e = (VariableComparision) exp;
            config.set(YMLBooleanNavigate.IS_NOTED, e.isNot());
            config.set(YMLBooleanNavigate.COMPARISON_TYPE, e.getComparisonType());
            config.set(YMLBooleanNavigate.COMPARISON_VALUE, e.getComparisonValue());
            config.createSection(YMLBooleanNavigate.COMPARISON_VAR);
            ConfigurationSection config2 = config.getConfigurationSection(YMLBooleanNavigate.COMPARISON_VAR);
            config2.set(YMLBooleanNavigate.GLOBAL_CATEGORY, e.getComparisonVarGlobal());
            config2.set(YMLBooleanNavigate.VAR_UID, e.getComparisonVarUID());
            config2.set(YMLBooleanNavigate.VAR_NAME, e.getComparisumVarName());
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

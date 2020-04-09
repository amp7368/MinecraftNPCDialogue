package apple.npc.creation.from_data.bool;

import apple.npc.data.booleanAlgebra.BooleanExp;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.booleanAlgebra.VariableComparision;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class WriteBoolean {
    public static void setConfig(ConfigurationSection config, Evaluateable booleanExp) {
        if (booleanExp instanceof BooleanExp) {
            // we're combining booleans
            BooleanExp bool = (BooleanExp) booleanExp;
            config.set(YMLBooleanNavigate.IS_AND_OP, bool.isAndOp());
            config.set(YMLBooleanNavigate.IS_NOTED, bool.isNot());
            WriteBoolean.setConfig(config.createSection(YMLBooleanNavigate.EXPRESSION_1), bool.getExp1());
            WriteBoolean.setConfig(config.createSection(YMLBooleanNavigate.EXPRESSION_2), bool.getExp2());
        } else if (booleanExp instanceof BooleanExpRequirement) {
            // we're probably a default expression
            BooleanExpRequirement bool = (BooleanExpRequirement) booleanExp;
            if (bool.isDefault())
                config.set(YMLBooleanNavigate.DEFAULT, bool.isDefaultVal());
            else
                WriteBoolean.setConfig(config.createSection(YMLBooleanNavigate.EXPRESSION), bool.getExp());

        } else if (booleanExp instanceof VariableComparision) {
            // we're comparing variables
            VariableComparision bool = (VariableComparision) booleanExp;
            config.set(YMLBooleanNavigate.IS_NOTED, bool.isNot());
            config.set(YMLBooleanNavigate.IS_CONCLUSION_VAR, bool.isConclusionVar());
            config.set(YMLBooleanNavigate.COMPARISON_TYPE, bool.getComparisonType());
            config.set(YMLBooleanNavigate.COMPARISON_VALUE, bool.getComparisonValue());
            ConfigurationSection configVar = config.createSection(YMLBooleanNavigate.COMPARISON_VAR);
            configVar.set(YMLBooleanNavigate.GLOBAL_CATEGORY,bool.getComparisonVarGlobal());
            configVar.set(YMLBooleanNavigate.VAR_UID,bool.getComparisonVarUID());

        }
    }
}

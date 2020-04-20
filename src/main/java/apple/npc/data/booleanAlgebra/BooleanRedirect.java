package apple.npc.data.booleanAlgebra;

import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class BooleanRedirect {
    public static Evaluateable make(ConfigurationSection config) {
        ConfigurationSection exp1 = config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_1);
        if (exp1 == null) {
            // then we should get either the default value or the variable comparison
            if (!config.contains(YMLBooleanNavigate.COMPARISON_TYPE)) {
                if (config.contains(YMLBooleanNavigate.TRACKING_TYPE)) {
                    return new BooleanHasItem(config);
                } else // we should get the default value
                    return new BooleanExpRequirement(config);
            } else {
                // we should get the variable comparison
                return new VariableComparision(config);
            }
        } else {
            // we should get the BooleanExp
            return new BooleanDoubleExp(config);
        }
    }
}

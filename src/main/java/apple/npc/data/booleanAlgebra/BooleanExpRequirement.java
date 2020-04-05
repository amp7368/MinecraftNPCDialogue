package apple.npc.data.booleanAlgebra;

import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class BooleanExpRequirement implements Evaluateable {
    private boolean isDefault;
    private boolean defaultVal;
    private Evaluateable exp;

    public BooleanExpRequirement(ConfigurationSection config) {
        ConfigurationSection exp1 = config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_1);
        if (exp1 == null) {
            // then we should get either the default value or the variable comparison
            if (!config.contains(YMLBooleanNavigate.COMPARISON_TYPE)) {
                // we should get the default value
                isDefault = true;
                defaultVal = config.getBoolean(YMLBooleanNavigate.DEFAULT);
            } else {
                // we should get the variable comparison
                isDefault = false;
                exp = new VariableComparision(config);
            }
        } else {
            // we should get the BooleanExp
            isDefault = false;
            exp = new BooleanExp(config);
        }
    }

    @Override
    public boolean evaluate(String playerUID, int currentConclusion) {
        if (isDefault)
            return defaultVal;
        return exp.evaluate(playerUID, currentConclusion);
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isDefaultVal() {
        return defaultVal;
    }

    public Evaluateable getExp() {
        return exp;
    }
}

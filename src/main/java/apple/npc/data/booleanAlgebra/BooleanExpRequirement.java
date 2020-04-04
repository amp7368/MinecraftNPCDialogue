package apple.npc.data.booleanAlgebra;

import org.bukkit.configuration.ConfigurationSection;

public class BooleanExpRequirement implements Evaluateable {
    private boolean isDefault;
    private boolean defaultVal;
    private Evaluateable exp;
    private boolean not;

    public BooleanExpRequirement(ConfigurationSection config) {
        ConfigurationSection exp1 = config.getConfigurationSection("exp1");
        if (exp1 == null) {
            // then we should get either the default value or the variable comparison
            if (!config.contains("comparisonType")) {
                // we should get the default value
                isDefault = true;
                defaultVal = config.getBoolean("default");
            } else {
                // we should get the variable comparison
                isDefault = false;
                not = false;
                exp = new VariableComparision(config);
            }
        } else {
            // we should get the BooleanExp
            isDefault = false;
            not = false;
            exp = new BooleanExp(config);
        }
    }

    @Override
    public boolean evaluate(String playerUID) {
        if (isDefault)
            return defaultVal;
        boolean expbool = exp.evaluate(playerUID);
        if (not)
            expbool = !expbool;
        return expbool;
    }
}

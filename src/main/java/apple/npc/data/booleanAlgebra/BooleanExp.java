package apple.npc.data.booleanAlgebra;

import org.bukkit.configuration.ConfigurationSection;

public class BooleanExp implements Evaluateable {
    private boolean isAndOp;
    private boolean isNoted;
    private Evaluateable exp1;
    private Evaluateable exp2;

    public BooleanExp(ConfigurationSection config) {
        isAndOp = config.getBoolean("isAndOp");
        isNoted = config.getBoolean("isNoted");
        exp1 = new BooleanExpRequirement(config.getConfigurationSection("exp1"));
        exp2 = new BooleanExpRequirement(config.getConfigurationSection("exp2"));
    }

    @Override
    public boolean evaluate(String playerUID) {
        boolean exp1Bool = exp1.evaluate(playerUID);
        boolean exp2Bool = exp2.evaluate(playerUID);
        boolean result;
        if (isAndOp)
            result = exp1Bool && exp2Bool;
        else
            result = exp1Bool || exp2Bool;
        if (isNoted)
            result = !result;
        return result;
    }
}

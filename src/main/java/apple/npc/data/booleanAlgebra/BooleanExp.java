package apple.npc.data.booleanAlgebra;

import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class BooleanExp implements Evaluateable {
    private boolean isAndOp;
    private boolean isNoted;
    private Evaluateable exp1;
    private Evaluateable exp2;

    public BooleanExp(ConfigurationSection config) {
        isAndOp = config.getBoolean(YMLBooleanNavigate.IS_AND_OP);
        isNoted = config.getBoolean(YMLBooleanNavigate.IS_NOTED);
        exp1 = new BooleanExpRequirement(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_1));
        exp2 = new BooleanExpRequirement(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_2));
    }

    @Override
    public boolean evaluate(String playerUID, int currentConclusion) {
        boolean exp1Bool = exp1.evaluate(playerUID, currentConclusion);
        boolean exp2Bool = exp2.evaluate(playerUID, currentConclusion);
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

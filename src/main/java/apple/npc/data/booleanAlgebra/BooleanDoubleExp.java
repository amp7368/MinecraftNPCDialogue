package apple.npc.data.booleanAlgebra;

import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BooleanDoubleExp implements Evaluateable {
    private boolean isAndOp;
    private boolean isNoted;
    private Evaluateable exp1;
    private Evaluateable exp2;

    public BooleanDoubleExp(ConfigurationSection config) {
        isAndOp = config.getBoolean(YMLBooleanNavigate.IS_AND_OP);
        isNoted = config.getBoolean(YMLBooleanNavigate.IS_NOTED);
        exp1 = BooleanRedirect.make(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_1));
        exp2 = BooleanRedirect.make(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_2));
    }

    public BooleanDoubleExp(boolean isAndOp, boolean isNoted, Evaluateable exp1, Evaluateable exp2) {
        this.isAndOp = isAndOp;
        this.isNoted = isNoted;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public boolean evaluate(Player player, int currentConclusion, long timeLastTalked) {
        boolean exp1Bool = exp1.evaluate(player, currentConclusion, timeLastTalked);
        boolean exp2Bool = exp2.evaluate(player, currentConclusion, timeLastTalked);
        boolean result;
        if (isAndOp)
            result = exp1Bool && exp2Bool;
        else
            result = exp1Bool || exp2Bool;
        if (isNoted)
            result = !result;
        return result;
    }

    public boolean isAndOp() {
        return isAndOp;
    }

    public boolean isNot() {
        return isNoted;
    }

    public Evaluateable getExp1() {
        return exp1;
    }

    public Evaluateable getExp2() {
        return exp2;
    }


}

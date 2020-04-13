package apple.npc.printing;

import apple.npc.data.booleanAlgebra.BooleanDoubleExp;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.booleanAlgebra.VariableComparision;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class PrintBoolean {

    public static void printAll(Evaluateable printingBoolean, Player player) {
        int layer = 0;
        boolean isComplete = true;
        int expNum;
        while (isComplete) {
            expNum = 0;
            StringBoolInt message = printLayer(printingBoolean, layer++, expNum);
            isComplete = message.bool;
            player.sendMessage(message.string);
        }
    }

    private static StringBoolInt printLayer(Evaluateable printingBoolean, int depth, int expNum) {
        if (printingBoolean instanceof BooleanDoubleExp) {
            return printDoubleExp((BooleanDoubleExp) printingBoolean, depth, expNum);
        } else if (printingBoolean instanceof BooleanExpRequirement) {
            return printExp((BooleanExpRequirement) printingBoolean, depth, expNum);
        } else if (printingBoolean instanceof VariableComparision) {
            return new StringBoolInt(printVarComp((VariableComparision) printingBoolean), true, expNum);
        }
        return new StringBoolInt(" §null ", true, expNum);
    }

    @NotNull
    private static String printVarComp(VariableComparision printingBoolean) {
        if (printingBoolean == null) {
            return " §null ";
        }
        StringBuilder string = new StringBuilder("");
        if (printingBoolean.isNot()) {
            string.append("§not ");
        }

        string.append("(");

        string.append(printingBoolean.getComparisonVarGlobal());
        string.append("-");
        string.append(printingBoolean.getComparisonVarUID());
        switch (printingBoolean.getComparisonType()) {
            case -2:
                string.append(" §< ");
                break;
            case -1:
                string.append(" §<= ");
                break;
            case 0:
                string.append(" §== ");
                break;
            case 1:
                string.append(" §>= ");
                break;
            default:
                string.append(" §> ");
        }
        string.append(printingBoolean.getComparisonValue());
        string.append(")");
        return string.toString();
    }

    @Nonnull
    private static StringBoolInt printExp(BooleanExpRequirement printingBoolean, int depth, int expNum) {
        if (depth == 0) {
            return new StringBoolInt("exp" + expNum++, true, expNum);
        }
        if (printingBoolean == null) {
            return new StringBoolInt(" §null ", true, expNum);
        }

        if (printingBoolean.isDefault())
            return new StringBoolInt(String.valueOf(printingBoolean.isDefaultVal()), true, expNum);
        else
            return printLayer(printingBoolean.getExp(), depth - 1, expNum);
    }

    @NotNull
    private static StringBoolInt printDoubleExp(BooleanDoubleExp printingBoolean, int depth, int expNum) {
        StringBuilder string = new StringBuilder();
        boolean returnBool;
        if (printingBoolean.isNot())
            string.append("§not ");
        string.append("(");
        if (depth == 0) {
            string.append(" §exp");
            string.append(expNum++);
            string.append(" ");
            if (printingBoolean.isAndOp())
                string.append("§and");
            else
                string.append("§or");
            string.append(" §exp");
            string.append(expNum++);
            string.append(" ");
            returnBool = false;
        } else {
            StringBoolInt message1 = printLayer(printingBoolean.getExp1(), depth - 1, expNum);
            string.append(message1.string);
            if (printingBoolean.isAndOp())
                string.append(" §and ");
            else
                string.append(" §or ");
            expNum = message1.integer;
            StringBoolInt message2 = printLayer(printingBoolean.getExp2(), depth - 1, expNum);
            string.append(message2.string);
            returnBool = message1.bool && message2.bool;
            expNum = message2.integer;
        }
        string.append(")");
        return new StringBoolInt(string.toString(), returnBool, expNum);
    }
}

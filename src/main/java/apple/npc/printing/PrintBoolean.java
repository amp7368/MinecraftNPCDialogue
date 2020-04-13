package apple.npc.printing;

import apple.npc.data.booleanAlgebra.BooleanExp;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.booleanAlgebra.VariableComparision;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class PrintBoolean {
    private static int expNum;

    public static void printAll(Evaluateable printingBoolean, Player player) {
        int layer = 0;
        boolean isComplete = true;
        while (isComplete) {
            expNum = 0;
            StringBool message = printLayer(printingBoolean, layer++);
            isComplete = message.bool;
            player.sendMessage(message.string);
        }
    }

    private static StringBool printLayer(Evaluateable printingBoolean, int depth) {
        if (printingBoolean instanceof BooleanExp) {
            return printDoubleExp((BooleanExp) printingBoolean, depth);
        } else if (printingBoolean instanceof BooleanExpRequirement) {
            return printExp((BooleanExpRequirement) printingBoolean, depth);
        } else if (printingBoolean instanceof VariableComparision) {
            return new StringBool(printVarComp((VariableComparision) printingBoolean), true);
        }
        return new StringBool(" §null ", true);
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
    private static StringBool printExp(BooleanExpRequirement printingBoolean, int depth) {
        if (depth == 0) {
            return new StringBool("exp" + expNum++, true);
        }
        if (printingBoolean == null) {
            return new StringBool(" §null ", true);
        }

        if (printingBoolean.isDefault())
            return new StringBool(String.valueOf(printingBoolean.isDefaultVal()), true);
        else
            return printLayer(printingBoolean.getExp(), depth - 1);
    }

    @NotNull
    private static StringBool printDoubleExp(BooleanExp printingBoolean, int depth) {
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
            StringBool message1 = printLayer(printingBoolean.getExp1(), depth - 1);
            string.append(message1.string);
            if (printingBoolean.isAndOp())
                string.append(" §and ");
            else
                string.append(" §or ");
            StringBool message2 = printLayer(printingBoolean.getExp2(), depth - 1);
            string.append(message2.string);
            returnBool = message1.bool && message2.bool;
        }
        string.append(")");
        return new StringBool(string.toString(), returnBool);
    }
}

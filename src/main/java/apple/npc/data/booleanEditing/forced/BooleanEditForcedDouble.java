package apple.npc.data.booleanEditing.forced;

import apple.npc.data.booleanAlgebra.BooleanDoubleExp;
import apple.npc.data.booleanAlgebra.Evaluateable;

public class BooleanEditForcedDouble implements BooleanEditForced {
    private boolean isAndOp;
    private boolean isNoted;
    private BooleanEditForced exp1;
    private BooleanEditForced exp2;

    private int name;

    public BooleanEditForcedDouble(int name) {
        exp1 = new BooleanEditForcedEmpty(name + 1);
        exp2 = new BooleanEditForcedEmpty(name + 2);
        this.name = name;
    }

    public BooleanEditForcedDouble(BooleanDoubleExp other, int name) {
        isAndOp = other.isAndOp();
        isNoted = other.isNot();
        exp1 = BooleanEditForcedRedirect.make(other.getExp1(), name + 1);
        exp2 = BooleanEditForcedRedirect.make(other.getExp2(), name + 2);
        this.name = name;
    }

    @Override
    public boolean isFinished() {
        return exp1.isFinished() && exp2.isFinished();
    }

    @Override
    public Evaluateable toFinished() {
        return null;
    }

    @Override
    public int nextExp() {
        if (!exp1.isFinished()) {
            return exp1.nextExp();
        }
        if (!exp2.isFinished()) {
            return exp2.nextExp();
        }
        return -1;
    }

    @Override
    public BooleanEditForced getLeftMost() {
        BooleanEditForced ex = exp1.getLeftMost();
        if (ex == null) {
            return exp2.getLeftMost();
        }
        return ex;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (isNoted) {
            string.append(" §not ");
        }
        string.append("( ");
        string.append('(');
        string.append(exp1.toString());
        string.append(')');
        if (isAndOp) {
            string.append(" §and ");
        } else
            string.append(" §or ");
        string.append('(');
        string.append(exp2.toString());
        string.append(')');
        string.append(" )");
        return string.toString();
    }
}

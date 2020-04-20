package apple.npc.data.booleanEditing.forced;

import apple.npc.commands.edit.boolean_algebra.data.VarComparisonObject;
import apple.npc.data.booleanAlgebra.BooleanDoubleExp;
import apple.npc.data.booleanAlgebra.Evaluateable;
import org.bukkit.inventory.ItemStack;

public class BooleanEditForcedDouble implements BooleanEditForced {
    private boolean isAndOp;
    private boolean isNoted;
    private BooleanEditForced exp1;
    private BooleanEditForced exp2;

    private BooleanEditForced parent;

    private int name;

    public BooleanEditForcedDouble(int name, BooleanEditForced parent) {
        exp1 = new BooleanEditForcedEmpty(name + 1, this);
        exp2 = new BooleanEditForcedEmpty(name + 2, this);
        this.parent = parent;
        this.name = name;

    }

    public BooleanEditForcedDouble(BooleanDoubleExp other, int name, BooleanEditForced parent) {
        isAndOp = other.isAndOp();
        isNoted = other.isNot();
        exp1 = BooleanEditForcedRedirect.make(other.getExp1(), name + 1, this);
        exp2 = BooleanEditForcedRedirect.make(other.getExp2(), name + 2, this);
        this.parent = parent;
        this.name = name;
    }

    public BooleanEditForcedDouble(boolean isNot, boolean isAnd, BooleanEditForced parent, int name) {
        isAndOp = isAnd;
        isNoted = isNot;
        exp1 = new BooleanEditForcedEmpty(name + 1, this);
        exp2 = new BooleanEditForcedEmpty(name + 2, this);
        this.parent = parent;
        this.name = name;
    }

    @Override
    public boolean isFinished() {
        return exp1.isFinished() && exp2.isFinished();
    }

    @Override
    public Evaluateable toFinished() {
        return new BooleanDoubleExp(isAndOp, isNoted, exp1.toFinished(), exp2.toFinished());
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
        string.append(' ');
        string.append(exp1.toString());
        string.append(' ');
        string.append(')');
        if (isAndOp) {
            string.append(" §and ");
        } else
            string.append(" §or ");
        string.append('(');
        string.append(' ');
        string.append(exp2.toString());
        string.append(' ');
        string.append(')');
        string.append(" )");
        return string.toString();
    }

    @Override
    public int getName() {
        return name;
    }

    @Override
    public BooleanEditForced getParent() {
        return parent;
    }

    @Override
    public int getBiggestName() {
        int name1 = exp1.getBiggestName();
        int name2 = exp2.getBiggestName();
        return Math.max(Math.max(name1, name2), name);
    }

    public void set(VarComparisonObject data) {
        if (exp1.isFinished()) {
            // fix exp2
            exp2 = new BooleanEditVarComparison(data, this);
        } else {
            exp1 = new BooleanEditVarComparison(data, this);
        }
    }

    public void set(boolean isNot, boolean isAnd, int name) {
        if (exp1.isFinished()) {
            // fix exp2
            exp2 = new BooleanEditForcedDouble(isNot, isAnd, this, name + 1);
        } else {
            exp1 = new BooleanEditForcedDouble(isNot, isAnd, this, name + 2);
        }
    }

    public void set(boolean isNot, ItemStack item, int trackingTye, int biggestName) {
        if (exp1.isFinished()) {
            // fix exp2
            exp2 = new BooleanEditForcedItem(isNot, item, trackingTye,this, name + 1);
        } else {
            exp1 = new BooleanEditForcedItem(isNot, item, trackingTye,this, name + 1);
        }
    }
}

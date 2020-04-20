package apple.npc.data.booleanEditing.forced;

import apple.npc.commands.edit.boolean_algebra.data.VarComparisonObject;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BooleanEditForcedExpBase implements BooleanEditForced {
    private boolean isDefault;
    private boolean defaultVal;
    private BooleanEditForced exp;
    private int name;
    private BooleanEditForced parent;

    public BooleanEditForcedExpBase(@NotNull BooleanExpRequirement other, int name, BooleanEditForced parent) {
        this.isDefault = other.isDefault();
        this.defaultVal = other.isDefaultVal();
        this.exp = BooleanEditForcedRedirect.make(other.getExp(), 1, this);
        this.parent = parent;
        this.name = 0;

    }

    public BooleanEditForcedExpBase(int name) {
        exp = new BooleanEditForcedEmpty(name + 1, this);
        this.name = 0;
    }

    @Override
    public boolean isFinished() {
        return exp.isFinished();
    }

    @Override
    public Evaluateable toFinished() {
        return new BooleanExpRequirement(isDefault, defaultVal, exp.toFinished());
    }

    @Override
    public int nextExp() {
        if (exp.isFinished())
            return exp.nextExp();
        return -1;
    }

    @Override
    public BooleanEditForced getLeftMost() {
        return exp.getLeftMost();
    }

    @Override
    public String toString() {
        if (isDefault)
            return String.valueOf(defaultVal);
        return exp.toString();
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
        return Math.max(name, exp.getBiggestName());
    }

    public void set(VarComparisonObject data) {
        this.exp = new BooleanEditVarComparison(data, this);
    }

    public void set(boolean isNot, boolean isAnd, int name) {
        this.exp = new BooleanEditForcedDouble(isNot, isAnd, this, name + 1);
    }

    public void set(boolean isNot, ItemStack itemInMainHand, int trackingTye, int biggestName) {
    }
}

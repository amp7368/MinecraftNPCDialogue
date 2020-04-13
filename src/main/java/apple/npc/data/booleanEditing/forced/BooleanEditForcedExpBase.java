package apple.npc.data.booleanEditing.forced;

import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import org.jetbrains.annotations.NotNull;

public class BooleanEditForcedExpBase implements BooleanEditForced {
    private boolean isDefault;
    private boolean defaultVal;
    private BooleanEditForced exp;
    private int name;

    public BooleanEditForcedExpBase(@NotNull BooleanExpRequirement other, int name) {
        this.isDefault = other.isDefault();
        this.defaultVal = other.isDefaultVal();
        this.exp = BooleanEditForcedRedirect.make(other.getExp(), 1);
        this.name = 0;
    }

    public BooleanEditForcedExpBase(int name) {
        exp = new BooleanEditForcedEmpty(name + 1);
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
}

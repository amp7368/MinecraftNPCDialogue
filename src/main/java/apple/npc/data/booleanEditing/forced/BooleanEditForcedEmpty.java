package apple.npc.data.booleanEditing.forced;

import apple.npc.data.booleanAlgebra.Evaluateable;

public class BooleanEditForcedEmpty implements BooleanEditForced {
    private int name;
    private BooleanEditForced parent;

    public BooleanEditForcedEmpty(int name, BooleanEditForced parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Evaluateable toFinished() {
        return null;
    }

    @Override
    public int nextExp() {
        return name;
    }

    @Override
    public BooleanEditForced getLeftMost() {
        return this;
    }

    @Override
    public String toString() {
        return "exp" + name;
    }

    @Override
    public int getName() {
        return name;
    }

    @Override
    public BooleanEditForced getParent() {
        return parent;
    }
}

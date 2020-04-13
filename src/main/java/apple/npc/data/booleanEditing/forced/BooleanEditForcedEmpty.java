package apple.npc.data.booleanEditing.forced;

import apple.npc.data.booleanAlgebra.Evaluateable;

public class BooleanEditForcedEmpty implements BooleanEditForced {
    private int name;

    public BooleanEditForcedEmpty(int name) {
        this.name = name;
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
}

package apple.npc.data.booleanEditing.forced;

import apple.npc.data.booleanAlgebra.Evaluateable;

public interface BooleanEditForced {
    boolean isFinished();
    Evaluateable toFinished();
    int nextExp();
}

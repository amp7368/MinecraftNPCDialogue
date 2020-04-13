package apple.npc.data.booleanEditing.forced;

import apple.npc.data.booleanAlgebra.BooleanDoubleExp;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.booleanAlgebra.VariableComparision;

public class BooleanEditForcedRedirect {

    public static BooleanEditForced make(Evaluateable exp, int name) {
        if (exp instanceof BooleanDoubleExp) {
            return new BooleanEditForcedDouble((BooleanDoubleExp) exp, name);
        } else if (exp instanceof BooleanExpRequirement) {
            return new BooleanEditForcedExpBase((BooleanExpRequirement) exp, name);
        } else if (exp instanceof VariableComparision) {
            return new BooleanEditVarComparison((VariableComparision) exp, name);
        } else {
            return new BooleanEditForcedEmpty(name);
        }
    }
}

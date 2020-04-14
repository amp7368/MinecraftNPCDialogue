package apple.npc.data.booleanEditing.forced;

import apple.npc.commands.edit.boolean_algebra.data.VarConcluComparisonObject;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.booleanAlgebra.VariableComparision;

public class BooleanEditVarComparison implements BooleanEditForced {
    private boolean isNoted;
    private int comparisonType;
    private long comparisonValue;

    private String comparisonVarGlobal;
    private String comparisonVarName;
    private int comparisonVarUID;
    private boolean isFinished;

    private BooleanEditForced parent;
    private int name;

    public BooleanEditVarComparison(VariableComparision other, int name, BooleanEditForced parent) {
        isNoted = other.isNot();
        comparisonType = other.getComparisonType();
        comparisonValue = other.getComparisonValue();
        comparisonVarGlobal = other.getComparisonVarGlobal();
        comparisonVarUID = other.getComparisonVarUID();
        comparisonVarName = other.getComparisumVarName();
        isFinished = true;
        this.parent = parent;
        this.name = name;
    }

    public BooleanEditVarComparison(VarConcluComparisonObject data, BooleanEditForced parent) {
        this.isNoted = data.isNot;
        this.comparisonType = data.type;
        this.comparisonValue = data.comparisonVal;
        this.comparisonVarGlobal = data.global;
        this.comparisonVarName = data.local;
        this.comparisonVarUID = data.localUID;
        this.parent = parent;
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public Evaluateable toFinished() {
        return new VariableComparision(isNoted, comparisonType, comparisonValue,
                comparisonVarGlobal, comparisonVarUID, comparisonVarName);
    }

    @Override
    public int nextExp() {
        if (isFinished) {
            return name;
        }
        return -1;
    }

    @Override
    public BooleanEditForced getLeftMost() {
        if (isFinished)
            return null;
        else
            return this;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (isNoted) {
            string.append(" §not ");
        }
        string.append('(');
        string.append(comparisonValue);
        string.append(' ');
        switch (comparisonType) {
            case -2:
                string.append("§<");
                break;
            case -1:
                string.append("§<=");
                break;
            case 0:
                string.append("§==");
                break;
            case 1:
                string.append("§>=");
                break;
            case 2:
                string.append("§>");
        }
        string.append(' ');
        string.append(comparisonVarGlobal);
        string.append("-");
        string.append(comparisonVarUID);
        string.append(')');
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
        return name;
    }
}

package apple.npc.commands.edit.boolean_algebra.data;

public class VarComparisonObject {
    public int comparisonVal;
    public String global;
    public String local;
    public int localUID;
    public int type;
    public boolean isNot;

    public VarComparisonObject() {
    }

    public void addComparisonVal(int val) {
        this.comparisonVal = val;
    }

    public void addComparisonGlobal(String global) {
        this.global = global;
    }

    public void addComparisonLocal(String local) {
        this.local = local;
    }

    public void addComparisonLocalUID(int localUID) {
        this.localUID = localUID;
    }

    public void addComparisonType(int type) {
        this.type = type;
    }

    public void addNot(boolean isNot) {
        this.isNot = isNot;
    }
}

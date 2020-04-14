package apple.npc.commands.edit.boolean_algebra.data;

public class VarConcluComparisonObject {
    public int npcUID;
    public int conclusionResult;
    public int comparisonVal;
    public String global;
    public String local;
    public int localUID;
    public int type;
    public boolean isNot;
    public boolean isConclusionVar;

    public VarConcluComparisonObject(int npcUID, int conclusionResult) {
        this.npcUID = npcUID;
        this.conclusionResult = conclusionResult;
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

    public void addConclusionVar(boolean isConclusionVar) {
        this.isConclusionVar = isConclusionVar;
    }
}

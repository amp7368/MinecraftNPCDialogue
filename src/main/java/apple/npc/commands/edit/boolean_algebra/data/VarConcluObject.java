package apple.npc.commands.edit.boolean_algebra.data;

public class VarConcluObject {
    int npcUID;
    int conclusionResult;
    int comparisonVal;

    public VarConcluObject(int npcUID, int conclusionResult) {
        this.npcUID = npcUID;
        this.conclusionResult = conclusionResult;
    }

    public void addComparisonVal(int val) {
        this.comparisonVal = val;
    }
}

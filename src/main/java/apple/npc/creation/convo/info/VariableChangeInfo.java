package apple.npc.creation.convo.info;

/**
 * Information for a variable change (in postResponses or defaultPostResponses)
 */
public class VariableChangeInfo {
    public int key;
    public String global;
    public int varUid;
    public int newValue;

    public VariableChangeInfo(int key, String global, int varUid, int newValue) {
        this.key = key;
        this.global = global;
        this.varUid = varUid;
        this.newValue = newValue;
    }
}

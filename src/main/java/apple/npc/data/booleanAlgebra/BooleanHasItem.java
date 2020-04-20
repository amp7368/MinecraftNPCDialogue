package apple.npc.data.booleanAlgebra;

public class BooleanHasItem implements Evaluateable {
    private boolean isNot;
    private String localName;
    private String displayName;
    private String material;
    private int slot;



    @Override
    public boolean evaluate(String playerUID, int currentConclusion, long timeLastTalked) {
        return false;
    }
}

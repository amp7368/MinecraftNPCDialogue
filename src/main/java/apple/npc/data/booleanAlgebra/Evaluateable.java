package apple.npc.data.booleanAlgebra;

public interface Evaluateable {
    boolean evaluate(String playerUID, int currentConclusion, long timeLastTalked);
}

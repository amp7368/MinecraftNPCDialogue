package apple.npc.creation.from_scratch.npc.info;

import apple.npc.data.booleanAlgebra.Evaluateable;

public class VarConcluInfo {
    public String key;
    public int conclusion;
    public Evaluateable exp;

    public VarConcluInfo(String key, int conclusion, Evaluateable exp) {
        this.key = key;
        this.conclusion = conclusion;
        this.exp = exp;
    }
}

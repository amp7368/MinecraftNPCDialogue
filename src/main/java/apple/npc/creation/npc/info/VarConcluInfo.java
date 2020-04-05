package apple.npc.creation.npc.info;

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

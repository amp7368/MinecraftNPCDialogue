package apple.npc.afer_boolean;

import apple.npc.MessageUtils;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.booleanAlgebra.Evaluateable;
import org.bukkit.entity.Player;


public class AfterVarConclu implements AfterVar {
    private int npcUID;
    private int conclusion;

    public AfterVarConclu(int npcUID, int concluNum) {
        this.npcUID = npcUID;
        this.conclusion = concluNum;
    }

    @Override
    public void dealWithDone(Player player, Evaluateable exp) {
        AllNPCs.setVarToConclu(npcUID, conclusion, exp);
        player.sendMessage(MessageUtils.GOOD + "You have set the varConclusion map");
    }
}

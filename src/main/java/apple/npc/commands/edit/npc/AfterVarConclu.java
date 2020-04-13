package apple.npc.commands.edit.npc;

import apple.npc.afer_boolean.AfterVar;
import apple.npc.data.booleanAlgebra.Evaluateable;
import org.bukkit.Bukkit;

public class AfterVarConclu implements AfterVar {
    private int npcUID;
    private int conclusionNum;

    public AfterVarConclu(int npcUID, int conclusionNum) {
        this.npcUID = npcUID;
        this.conclusionNum = conclusionNum;
    }

    @Override
    public void dealWithDone(Evaluateable exp) {
        Bukkit.getServer().broadcastMessage("dealing with done!");
    }
}

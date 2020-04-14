package apple.npc.afer_boolean;

import apple.npc.commands.edit.boolean_algebra.data.BooleanDataStore;
import apple.npc.commands.edit.boolean_algebra.data.NpcDataStore;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.booleanAlgebra.Evaluateable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AfterVarConclu implements AfterVar {
    @Override
    public void dealWithDone(Player player, Evaluateable exp) {
        UUID uid = player.getUniqueId();
        int conclusion = NpcDataStore.getConclu(uid);
        int npcUID = NpcDataStore.getNpcUid(uid);
        NpcDataStore.remove(uid);
        Bukkit.getServer().broadcastMessage("dealing with done!");
        AllNPCs.setVarToConclu(npcUID, conclusion, exp);
    }
}

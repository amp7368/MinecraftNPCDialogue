package apple.npc.afer_boolean;

import apple.npc.data.booleanAlgebra.Evaluateable;
import org.bukkit.entity.Player;

public interface AfterVar {
    void dealWithDone(Player player, Evaluateable exp);
}

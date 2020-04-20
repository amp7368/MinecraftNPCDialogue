package apple.npc.data.booleanAlgebra;

import org.bukkit.entity.Player;

public interface Evaluateable {
    boolean evaluate(Player player, int currentConclusion, long timeLastTalked);
}

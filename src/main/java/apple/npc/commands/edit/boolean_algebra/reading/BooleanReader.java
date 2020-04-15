package apple.npc.commands.edit.boolean_algebra.reading;

import org.bukkit.entity.Player;

public abstract class BooleanReader {
    private String line;

    public void setLine(String message) {
        this.line = message;
    }

    public abstract void dealWithStop(Player player);
}

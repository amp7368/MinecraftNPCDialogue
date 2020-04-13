package apple.npc.commands.edit.boolean_algebra.reading;

import apple.npc.reading.Reading;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.entity.Player;

public class ReadBooleanCompGlobal extends ReadingCommand {

    @Override
    public void dealWithStop(Player player) {
        player.sendMessage("dealing with stop for readBooleanCompGlobal");
    }
}

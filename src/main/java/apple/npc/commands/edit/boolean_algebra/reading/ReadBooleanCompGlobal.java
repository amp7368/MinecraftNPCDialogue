package apple.npc.commands.edit.boolean_algebra.reading;

import apple.npc.commands.StopCommand;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarConcluDataStore;
import apple.npc.reading.Reading;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.entity.Player;

public class ReadBooleanCompGlobal extends ReadingCommand {

    @Override
    public void dealWithStop(Player player) {
        player.sendMessage("dealing with stop for readBooleanCompGlobal");
        BooleanVarConcluDataStore.get(player.getUniqueId()).addComparisonGlobal(command);

        player.sendMessage("What is the name of the player variable?");
        StopCommand.startListening(new ReadBooleanCompVarName(), player);
    }
}

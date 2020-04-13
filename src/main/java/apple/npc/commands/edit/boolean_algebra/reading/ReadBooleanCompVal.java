package apple.npc.commands.edit.boolean_algebra.reading;

import apple.npc.MessageUtils;
import apple.npc.commands.StopCommand;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarConcluDataStore;
import apple.npc.commands.edit.boolean_algebra.data.VarConcluObject;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.entity.Player;

public class ReadBooleanCompVal extends ReadingCommand {

    @Override
    public void dealWithStop(Player player) {
        player.sendMessage("Dealing with stop in ReadBooleanCompVal");
        int val;
        try {
            val = Integer.parseInt(command);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "This argument must be a number");
            StopCommand.startListening(new ReadBooleanCompVal(), player);
            return;
        }
        VarConcluObject object = BooleanVarConcluDataStore.get(player.getUniqueId());
        object.addComparisonVal(val);
        player.sendMessage("What is the global category of the player variable?");
        StopCommand.startListening(new ReadBooleanCompGlobal(), player);
    }
}

package apple.npc.commands.edit.boolean_algebra.reading.comp;

import apple.npc.MessageUtils;
import apple.npc.commands.StopCommand;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarConcluCompDataStore;
import apple.npc.commands.edit.boolean_algebra.data.VarConcluComparisonObject;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.entity.Player;

public class ReadBooleanCompVal extends ReadingCommand {

    @Override
    public void dealWithStop(Player player) {
        int val;
        try {
            val = Integer.parseInt(command);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "This argument must be a number");
            StopCommand.startListening(new ReadBooleanCompVal(), player);
            return;
        }
        player.sendMessage(MessageUtils.LONG_DASH);
        VarConcluComparisonObject object = BooleanVarConcluCompDataStore.get(player.getUniqueId());
        object.addComparisonVal(val);
        player.sendMessage(MessageUtils.EDITING + "What is the global category of the player variable?");
        StopCommand.startListening(new ReadBooleanCompGlobal(), player);
        player.sendMessage(MessageUtils.LONG_DASH);
    }
}

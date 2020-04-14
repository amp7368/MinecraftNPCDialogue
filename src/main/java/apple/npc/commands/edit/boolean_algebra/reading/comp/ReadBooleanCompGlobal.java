package apple.npc.commands.edit.boolean_algebra.reading.comp;

import apple.npc.MessageUtils;
import apple.npc.commands.StopCommand;
import apple.npc.commands.edit.boolean_algebra.BooleanSessionStart;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarConcluCompDataStore;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.entity.Player;

public class ReadBooleanCompGlobal extends ReadingCommand {

    @Override
    public void dealWithStop(Player player) {
        BooleanVarConcluCompDataStore.get(player.getUniqueId()).addComparisonGlobal(command);

        player.sendMessage(MessageUtils.LONG_DASH);
        player.sendMessage(MessageUtils.EDITING + "What is the name of the player variable?");
        StopCommand.startListening(new ReadBooleanCompVarName(), player);
        player.sendMessage(MessageUtils.LONG_DASH);
    }
}

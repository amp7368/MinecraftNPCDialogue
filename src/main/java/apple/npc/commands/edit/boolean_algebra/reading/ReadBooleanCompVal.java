package apple.npc.commands.edit.boolean_algebra.reading;

import apple.npc.MessageUtils;
import apple.npc.commands.StopCommand;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarConcluCompDataStore;
import apple.npc.commands.edit.boolean_algebra.data.VarConcluComparisonObject;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReadBooleanCompVal extends ReadingCommand {
    private JavaPlugin plugin;

    public ReadBooleanCompVal(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public void dealWithStop(Player player) {
        int val;
        try {
            val = Integer.parseInt(command);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "This argument must be a number");
            StopCommand.startListening(new ReadBooleanCompVal(plugin), player);
            return;
        }
        player.sendMessage(MessageUtils.LONG_DASH);
        VarConcluComparisonObject object = BooleanVarConcluCompDataStore.get(player.getUniqueId());
        object.addComparisonVal(val);
        player.sendMessage(MessageUtils.EDITING + "What is the global category of the player variable?");
        StopCommand.startListening(new ReadBooleanCompGlobal(plugin), player);
        player.sendMessage(MessageUtils.LONG_DASH);
    }
}

package apple.npc.commands.edit.boolean_algebra.reading;

import apple.npc.MessageUtils;
import apple.npc.commands.StopCommand;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarCompDataStore;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReadBooleanCompGlobal extends ReadingCommand {
    private JavaPlugin plugin;

    public ReadBooleanCompGlobal(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public void dealWithStop(Player player) {
        BooleanVarCompDataStore.get(player.getUniqueId()).addComparisonGlobal(command);

        player.sendMessage(MessageUtils.LONG_DASH);
        player.sendMessage(MessageUtils.EDITING + "What is the name of the player variable?");
        StopCommand.startListening(new ReadBooleanCompVarName(plugin), player);
        player.sendMessage(MessageUtils.LONG_DASH);
    }
}

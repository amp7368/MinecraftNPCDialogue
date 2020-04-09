package apple.npc.commands.io;

import apple.npc.ColorScheme;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.WriteEverything;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class WriteCommand implements CommandExecutor {

    public WriteCommand(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_WRITE);
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the " + CommandReferences.NPC_WRITE + " command");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        WriteEverything.write();
        commandSender.sendMessage(ColorScheme.GOOD + "You just wrote a lot");
        return true;
    }
}
package apple.npc.commands.io;

import apple.npc.ColorScheme;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.ReadEverything;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class ReadCommand implements CommandExecutor {

    public ReadCommand(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_READ);
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the " + CommandReferences.NPC_READ + " command");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        ReadEverything.read();
        commandSender.sendMessage(ColorScheme.GOOD+"You just read a lot");
        return true;
    }
}

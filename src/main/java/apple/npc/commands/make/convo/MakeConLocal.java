package apple.npc.commands.make.convo;

import apple.npc.commands.CommandReferences;
import apple.npc.commands.CreateRedirect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for creating a local category for conversations given global and a local name
 */
public class MakeConLocal implements CommandExecutor, TabCompleter {

    public MakeConLocal(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_MAKE_LOCAL);
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the " + CommandReferences.NPC_CONVO_MAKE_LOCAL + " command");
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (args.length != 2) {
            commandSender.sendMessage(String.format(ChatColor.RED + "args length of %d is not valid.", args.length));
            return true;
        }
        CreateRedirect.createConvoLocal(args[0], args[1], player);
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        return null;
    }
}

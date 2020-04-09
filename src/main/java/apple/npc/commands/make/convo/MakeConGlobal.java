package apple.npc.commands.make.convo;

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
 * A class for dealing with making a global category for conversations
 */
public class MakeConGlobal implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public MakeConGlobal(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("npc_convo_make_global");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_convo_make_global command");
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
        if (args.length != 1) {
            commandSender.sendMessage(String.format(ChatColor.RED + "args length of %d is not valid.", args.length));
            return true;
        }
        CreateRedirect.createConvoGlobal(args[0], player);
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<String> tabComplete = new ArrayList<>();
        return null;
    }
}

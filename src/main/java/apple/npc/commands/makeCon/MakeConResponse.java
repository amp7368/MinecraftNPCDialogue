package apple.npc.commands.makeCon;

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
 * A class for dealing with creating responses for a conversation given a global, local String, and convo String
 */
public class MakeConResponse implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public MakeConResponse(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("make_con_response");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the make_con_response command");
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

        if (args.length != 3) {
            commandSender.sendMessage(String.format(ChatColor.RED + "args length of %d is not valid.", args.length));
            return true;
        }
        CreateRedirect.createConvoResponse(args[0], args[1], args[2], player);
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<String> tabComplete = new ArrayList<>();
        return null;
    }
}

package apple.npc.commands.edit.npc;

import apple.npc.MessageUtils;
import apple.npc.commands.EditRedirect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that deals with the editing of conclusionsToConvo in the  npc session
 */
public class EditNpcConclusionToConvo implements CommandExecutor, TabCompleter {
    public EditNpcConclusionToConvo(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand("npc_edit_conclusionsToConvo");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_edit_conclusionsToConvo command");
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }

        if (args.length != 1) {
            commandSender.sendMessage(String.format(ChatColor.RED + "args length of %d is not valid.", args.length));
            return false;
        }
        try {
            Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(MessageUtils.BAD + "The first argument must be a number.");
            return false;
        }
        EditRedirect.editNpcConcluToConvo(Integer.parseInt(args[0]), player);

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}
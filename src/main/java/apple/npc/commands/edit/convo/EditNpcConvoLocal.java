package apple.npc.commands.edit.convo;

import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.reading.command.convo.ReadingConvoLocal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EditNpcConvoLocal implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoLocal(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_LOCAL);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_LOCAL));
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
            return false;
        }
        player.sendMessage(ChatColor.BLUE + "Type what conversation local category would you like to edit?");

        String global = args[0];
        if (!AllConversations.hasGlobalCategory(global)) {
            player.sendMessage(ChatColor.RED + String.format("There is no %s conversation global category", global));
            return false;
        }

        StopCommand.startListening(new ReadingConvoLocal(args[0]), player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
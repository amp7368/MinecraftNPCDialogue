package apple.npc.commands.edit.convo;

import apple.npc.ColorScheme;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.reading.command.convo.ReadingConvoConvo;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EditNpcConvoConvo implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoConvo(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_CONVO);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_CONVO));
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
            player.sendMessage(ColorScheme.BAD + "Invalid number of arguments");
            return false;
        }
        String global = args[0];
        int local;
        try {
            local = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ColorScheme.BAD + "The second argument must be a number.");
            return false;
        }
        if (AllConversations.hasLocalCategory(global, local)) {
            player.sendMessage(ColorScheme.EDITING + "Type what conversation name you would you like to edit.");
            StopCommand.startListening(new ReadingConvoConvo(global, local), player);
        } else {
            player.sendMessage(ColorScheme.BAD + String.format("%s:%s is an invalid conversation category", global, local));

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
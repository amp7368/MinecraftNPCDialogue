package apple.npc.commands.edit.convo;

import apple.npc.commands.StopCommand;
import apple.npc.reading.command.convo.ReadingConvoGlobal;
import apple.npc.reading.command.npc.edit.ReadingNpcConclusionNum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EditNpcConvoGlobal implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoGlobal(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("npc_convo_edit_global");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_convo_edit_global command");
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
        player.sendMessage(ChatColor.BLUE + "Type what conversation global category would you like to edit?");
        StopCommand.startListening(new ReadingConvoGlobal(), player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

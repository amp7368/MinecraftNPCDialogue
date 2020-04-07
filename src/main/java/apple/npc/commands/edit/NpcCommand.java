package apple.npc.commands.edit;

import apple.npc.commands.EditRedirect;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * a class that deals with the npc session for editing npcs
 */
public class NpcCommand implements CommandExecutor, TabCompleter {
    public NpcCommand(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand("npc");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc command");
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
        EditRedirect.editNpc(player);

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}

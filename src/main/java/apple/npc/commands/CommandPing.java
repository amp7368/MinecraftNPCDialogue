package apple.npc.commands;

import apple.npc.NPCDialogueMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandPing implements CommandExecutor {
    JavaPlugin plugin;

    public CommandPing(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("ping");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the ping command");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
        } else {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "pong");
        }
        return false;
    }
}

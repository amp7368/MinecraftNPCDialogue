package apple.npc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GetUID implements CommandExecutor {
    JavaPlugin plugin;

    public GetUID(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("npc_getuid");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_getuid command");
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
            for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), 2, 2, 2)){
                player.sendMessage(entity.getName() + " - " + entity.getUniqueId().toString());
                System.out.println(entity.getName() + " - " + entity.getUniqueId().toString());
            }
        }
        return false;
    }
}

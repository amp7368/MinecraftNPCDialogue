package apple.npc.commands;

import apple.npc.reading.Reading;
import apple.npc.reading.command.ReadingCommand;
import apple.npc.reading.text.ReadingText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class StopCommand implements CommandExecutor, Listener {
    private static HashMap<Player, Reading> reading;
    private static JavaPlugin plugin;

    public StopCommand(JavaPlugin pl) {
        reading = new HashMap<>();
        plugin = pl;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        PluginCommand command = plugin.getCommand("con_stop");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the con_stop command");
            return;
        }
        command.setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (reading.containsKey(player) && reading.get(player) instanceof ReadingText) {
            // otherwise finish what you were doing with the text
            Reading read = reading.remove(player);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> read.dealWithStop(player), 1);
            return true;
        }
        player.sendMessage("We weren't recording whatever you just wrote");
        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (reading.containsKey(player)) {
            Reading read = reading.get(player);
            if (read instanceof ReadingText)
                ((ReadingText) read).addText(event.getMessage());
            else if (read instanceof ReadingCommand) {
                ReadingCommand readCommand = (ReadingCommand) read;
                readCommand.setCommand(event.getMessage());
                readCommand.dealWithStop(player);
                reading.remove(player);
            }
        }
    }

    public static void startListening(Reading readingText, Player player) {
        reading.put(player, readingText);
    }
}

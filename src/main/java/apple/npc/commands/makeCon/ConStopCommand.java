package apple.npc.commands.makeCon;

import apple.npc.utils.ReadingText;
import apple.npc.utils.ReadingTextConvo;
import apple.npc.utils.ReadingTextResponse;
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

public class ConStopCommand implements CommandExecutor, Listener {
    private static HashMap<Player, ReadingText> texts;

    public ConStopCommand(JavaPlugin plugin) {
        texts = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
        PluginCommand command = plugin.getCommand("con_stop");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the con_stop command");
            return;
        }
        command.setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (!texts.containsKey(player)) {
            player.sendMessage("We weren't recording whatever you just wrote");
            return false;
        }
        // otherwise finish what you were doing with the text
        texts.remove(player).dealWithStop(player);
        return true;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (texts.containsKey(player)) {
            texts.get(player).addText(event.getMessage());
        }
    }


    public static void startListening(String global, int local, int convoUID, Player player) {
        texts.put(player, new ReadingTextResponse(global, local, convoUID));
    }

    public static void startListening(String global, int local, String convoUID, Player player) {
        texts.put(player, new ReadingTextConvo(global, local, convoUID));
    }
}

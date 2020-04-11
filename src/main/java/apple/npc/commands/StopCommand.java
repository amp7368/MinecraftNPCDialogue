package apple.npc.commands;

import apple.npc.ColorScheme;
import apple.npc.reading.Reading;
import apple.npc.reading.command.ReadingCommand;
import apple.npc.reading.text.ReadingText;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

/**
 * a class for reading text or commands from the player during editing sessions
 */
public class StopCommand implements CommandExecutor, Listener {
    private static HashMap<UUID, Reading> reading;
    private static JavaPlugin plugin;

    /**
     * initiates the StopCommand ChatListener and CommandExecutor
     *
     * @param pl the npc plugin
     */
    public StopCommand(JavaPlugin pl) {
        reading = new HashMap<>();
        plugin = pl;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        PluginCommand command = plugin.getCommand("stop_reading");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the stop_reading command");
            return;
        }
        command.setExecutor(this);

        command = plugin.getCommand("abort_reading");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the abort_reading command");
            return;
        }
        command.setExecutor(this);
    }

    /**
     * when the player says stop listening to me and write down what I said
     *
     * @param commandSender the sender of the command
     * @param command       the command that was sent (ignored)
     * @param s             the String? (ignored
     * @param args          the args of the command (ignored)
     * @return whether the command worked as the player intended
     */
    @Override
    public boolean onCommand(CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (command.getName().equals("abort_reading")) {
            reading.remove(player.getUniqueId());
            player.sendMessage("We're not listening anymore.");
            return true;
        } else {
            if (reading.containsKey(player.getUniqueId()) && reading.get(player.getUniqueId()) instanceof ReadingText) {
                // otherwise finish what you were doing with the text
                Reading read = reading.remove(player.getUniqueId());
                read.dealWithStop(player);
                return true;
            }
            player.sendMessage(ChatColor.GRAY + "We weren't recording whatever you just wrote");
            return false;
        }
    }

    /**
     * Listens for any chat event and if we're listening to the player, record what they say
     * todo make it so that there is a timeout of an hour or so
     *
     * @param event the chat event
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (reading.containsKey(player.getUniqueId())) {
            Reading read = reading.get(player.getUniqueId());
            if (read instanceof ReadingText) {
                player.sendMessage(ColorScheme.LONG_DASH);

                net.md_5.bungee.api.chat.TextComponent stop = new TextComponent();
                stop.setText("(Finish Reading)");
                stop.setUnderlined(true);
                stop.setColor(ColorScheme.STOP);
                stop.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stop_reading"));
                player.spigot().sendMessage(stop);

                net.md_5.bungee.api.chat.TextComponent abort = new TextComponent();
                abort.setText("(Abort Reading)");
                abort.setUnderlined(true);
                abort.setColor(ColorScheme.STOP);
                abort.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/abort_reading"));
                player.spigot().sendMessage(abort);

                player.sendMessage(event.getMessage());

                ((ReadingText) read).addText(event.getMessage());

                player.sendMessage(ColorScheme.LONG_DASH);
                event.setCancelled(true);
            } else if (read instanceof ReadingCommand) {
                ReadingCommand readCommand = (ReadingCommand) read;
                readCommand.setCommand(event.getMessage());
                reading.remove(player.getUniqueId());
                readCommand.dealWithStop(player);

                player.sendMessage(event.getMessage());
                event.setCancelled(true);
            }
        }
    }

    /**
     * start listening for the event and give a link to the class that will be called when we're done listening
     *
     * @param read   what class deals with stopping
     * @param player the player we're listening to
     */
    public static void startListening(Reading read, Player player) {
        reading.put(player.getUniqueId(), read);
    }
}

package apple.npc.commands;

import apple.npc.MessageUtils;
import apple.npc.reading.Reading;
import apple.npc.reading.command.ReadingCommand;
import apple.npc.reading.text.ReadingText;
import net.md_5.bungee.api.ChatMessageType;
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
import java.util.HashMap;
import java.util.UUID;

/**
 * a class for reading text or commands from the player during editing sessions
 */
public class StopCommand implements CommandExecutor, Listener {
    private static final int LISTENING_SECS = 60 * 3;
    private static final int LISTENING_TICKS = 20 * LISTENING_SECS; // 3 minutes
    private static final long LISTENING_MILLIS = 1000 * LISTENING_SECS;
    private static HashMap<UUID, Reading> reading;
    private static HashMap<UUID, Long> lastRead;
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
        UUID playerUID = player.getUniqueId();
        if (command.getName().equals("abort_reading")) {
            reading.remove(playerUID);
            player.sendMessage(MessageUtils.BAD + "We're not listening anymore.");
            return true;
        } else {
            if (lastRead.containsKey(playerUID)) {
                if (System.currentTimeMillis() - lastRead.get(playerUID) > LISTENING_MILLIS) {
                    lastRead.remove(playerUID);
                    reading.remove(playerUID);
                }
            }
            if (reading.containsKey(playerUID) && reading.get(playerUID) instanceof ReadingText) {
                // otherwise finish what you were doing with the text
                Reading read = reading.remove(playerUID);
                lastRead.remove(playerUID);
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
        UUID playerUID = player.getUniqueId();
        if (lastRead.containsKey(playerUID)) {
            if (System.currentTimeMillis() - lastRead.get(playerUID) > LISTENING_MILLIS) {
                lastRead.remove(playerUID);
                reading.remove(playerUID);
            }
        }
        if (reading.containsKey(playerUID)) {
            Reading read = reading.get(playerUID);
            if (read instanceof ReadingText) {
                player.sendMessage(MessageUtils.LONG_DASH);

                net.md_5.bungee.api.chat.TextComponent stop = new TextComponent();
                stop.setText("(Finish Reading)");
                stop.setUnderlined(true);
                stop.setColor(MessageUtils.STOP);
                stop.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stop_reading"));
                player.spigot().sendMessage(stop);

                showAbort(player);
                player.sendMessage(event.getMessage());

                ((ReadingText) read).addText(event.getMessage());

                player.sendMessage(MessageUtils.LONG_DASH);
                event.setCancelled(true);
            } else if (read instanceof ReadingCommand) {
                ReadingCommand readCommand = (ReadingCommand) read;
                readCommand.setCommand(event.getMessage());
                lastRead.remove(playerUID);
                reading.remove(playerUID);
                readCommand.dealWithStop(player);

                player.sendMessage(event.getMessage());
                event.setCancelled(true);
            }
        }
    }

    public static void showAbort(Player player) {
        player.sendMessage(MessageUtils.DASH);
        net.md_5.bungee.api.chat.TextComponent abort = new TextComponent();
        abort.setText("(Abort Reading)");
        abort.setUnderlined(true);
        abort.setColor(MessageUtils.STOP);
        abort.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/abort_reading"));
        player.spigot().sendMessage(abort);
    }

    /**
     * start listening for the event and give a link to the class that will be called when we're done listening
     *
     * @param read   what class deals with stopping
     * @param player the player we're listening to
     */
    public static void startListening(Reading read, Player player) {
        reading.put(player.getUniqueId(), read);
        showAbort(player);
        player.sendTitle(ChatColor.RED + "Listening", ChatColor.RED + "To abort: /abort_reading", 20, LISTENING_TICKS, 20);
    }
}

package apple.npc.commands;

import apple.npc.ColorScheme;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class NpcCommand implements CommandExecutor {
    JavaPlugin plugin;

    public NpcCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC);
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the " + CommandReferences.NPC + " command");
            return;
        }
        command.setExecutor(this);
    }


    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        player.sendMessage(ColorScheme.LONG_DASH);

        player.sendMessage(net.md_5.bungee.api.ChatColor.BLUE + "What would you like to do?");

        TextComponent editNpcs = new TextComponent();
        editNpcs.setText("(Edit Npcs)");
        editNpcs.setUnderlined(true);
        editNpcs.setColor(ChatColor.GREEN);
        editNpcs.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit"));

        TextComponent editConversations = new TextComponent();
        editConversations.setText("(Edit Conversations)");
        editConversations.setUnderlined(true);
        editConversations.setColor(ChatColor.GREEN);
        editConversations.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_convo_edit"));

        TextComponent makeNpcs = new TextComponent();
        makeNpcs.setText("(make Npcs)");
        makeNpcs.setUnderlined(true);
        makeNpcs.setColor(ChatColor.GREEN);
        makeNpcs.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_make"));

        TextComponent makeConversations = new TextComponent();
        makeConversations.setText("(make Conversations)");
        makeConversations.setUnderlined(true);
        makeConversations.setColor(ChatColor.GREEN);
        makeConversations.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_convo_make"));

        TextComponent separator = new TextComponent();
        separator.setText("   ");

        player.spigot().sendMessage(editNpcs, separator, editConversations, separator, makeNpcs, separator, makeConversations);

        player.sendMessage(ColorScheme.LONG_DASH);
        return true;
    }
}

package apple.npc.commands.edit.boolean_algebra.commands;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BooleanItemTrackingCommand implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public BooleanItemTrackingCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_TRACKING);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_TRACKING));
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (args.length != 1) {
            player.sendMessage(String.format("%d is an invalid arguments length", args.length));
            return false;
        }

        TextComponent welcome = new TextComponent();
        welcome.setText("What would you like me to track about the item the player must have?\n(if you don't know what localizedName is, don't use it)\n(displayName is what the player sees as the item name)");
        welcome.setColor(ChatColor.BLUE);
        player.spigot().sendMessage(welcome);

        TextComponent both = new TextComponent();
        both.setText("(type & displayName & localizedName) == hasItem");
        both.setUnderlined(true);
        both.setColor(MessageUtils.EDITING_OPTION);
        both.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d %d",
                CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_SET, 0,3,0)));
        player.spigot().sendMessage(both);

        TextComponent display = new TextComponent();
        display.setText("(type & displayName) == hasItem");
        display.setUnderlined(true);
        display.setColor(MessageUtils.EDITING_OPTION);
        display.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d %d",
                CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_SET, 0,2,0)));
        player.spigot().sendMessage(display);

        TextComponent localized = new TextComponent();
        localized.setText("(type & localizedName) == hasItem");
        localized.setUnderlined(true);
        localized.setColor(MessageUtils.EDITING_OPTION);
        localized.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d %d",
                CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_SET, 0,1,0)));
        player.spigot().sendMessage(localized);

        TextComponent none = new TextComponent();
        none.setText("(type) == hasItem");
        none.setUnderlined(true);
        none.setColor(MessageUtils.EDITING_OPTION);
        none.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d %d",
                CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_SET, 0,0,0)));
        player.spigot().sendMessage(none);


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
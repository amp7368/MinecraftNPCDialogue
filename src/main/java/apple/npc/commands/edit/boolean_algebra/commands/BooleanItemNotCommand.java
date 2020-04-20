package apple.npc.commands.edit.boolean_algebra.commands;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BooleanItemNotCommand implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public BooleanItemNotCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM));
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        TextComponent hasItem = new TextComponent();
        hasItem.setText("(hasItem)");
        hasItem.setUnderlined(true);
        hasItem.setColor(MessageUtils.EDITING_OPTION);
        hasItem.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d",
                CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_TRACKING, 0)));
        player.spigot().sendMessage(hasItem);

        player.sendMessage(MessageUtils.DASH);

        TextComponent notHasItem = new TextComponent();
        notHasItem.setText("not (hasItem)");
        notHasItem.setUnderlined(true);
        notHasItem.setColor(MessageUtils.EDITING_OPTION);
        notHasItem.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d",
                CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_TRACKING, 1)));
        player.spigot().sendMessage(notHasItem);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
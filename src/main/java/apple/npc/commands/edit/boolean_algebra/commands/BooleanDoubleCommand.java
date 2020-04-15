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

public class BooleanDoubleCommand implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public BooleanDoubleCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS_SPECIFIC_DOUBLE);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS_SPECIFIC_DOUBLE));
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

        // ask the player whether it should be an and op or nah

        TextComponent and = new TextComponent();
        and.setText("(exp and exp)");
        and.setColor(MessageUtils.EDITING_OPTION);
        and.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_DOUBLE_TYPE, 1, 0)));
        player.spigot().sendMessage(and);

        TextComponent or = new TextComponent();
        or.setText("(exp or exp)");
        or.setColor(MessageUtils.EDITING_OPTION);
        or.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_DOUBLE_TYPE, 0, 0)));
        player.spigot().sendMessage(or);


        TextComponent andNot = new TextComponent();
        andNot.setText("not (exp and exp)");
        andNot.setColor(MessageUtils.EDITING_OPTION);
        andNot.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_DOUBLE_TYPE, 1, 1)));
        player.spigot().sendMessage(andNot);

        TextComponent orNot = new TextComponent();
        orNot.setText("not (exp or exp)");
        orNot.setColor(MessageUtils.EDITING_OPTION);
        orNot.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_DOUBLE_TYPE, 0, 1)));
        player.spigot().sendMessage(orNot);


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
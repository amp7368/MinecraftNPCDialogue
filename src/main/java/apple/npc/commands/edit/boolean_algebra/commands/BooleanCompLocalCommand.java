package apple.npc.commands.edit.boolean_algebra.commands;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarCompDataStore;
import apple.npc.commands.edit.boolean_algebra.data.VarComparisonObject;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BooleanCompLocalCommand implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public BooleanCompLocalCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_LOCAL);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_LOCAL));
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
        if (args.length != 1) {
            player.sendMessage(MessageUtils.BAD + String.format("args length of %d is invalid", args.length));
            return false;
        }

        int localUID;
        try {
            localUID = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The first argument must be a number");
            return false;
        }

        VarComparisonObject data = BooleanVarCompDataStore.get(player.getUniqueId());

        data.addComparisonLocalUID(localUID);

        player.sendMessage(MessageUtils.LONG_DASH);
        player.sendMessage(MessageUtils.EDITING + "What is the comparison type?");

        TextComponent lt = new TextComponent();
        lt.setText(String.format("%d < %s-%s", data.comparisonVal, data.global, data.local));
        lt.setColor(MessageUtils.EDITING_OPTION);
        lt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, -2)));
        player.spigot().sendMessage(lt);

        TextComponent lte = new TextComponent();
        lte.setText(String.format("%d <= %s-%s", data.comparisonVal, data.global, data.local));
        lte.setColor(MessageUtils.EDITING_OPTION);
        lte.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, -1)));
        player.spigot().sendMessage(lte);

        TextComponent ee = new TextComponent();
        ee.setText(String.format("%d == %s-%s", data.comparisonVal, data.global, data.local));
        ee.setColor(MessageUtils.EDITING_OPTION);
        ee.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, 0)));
        player.spigot().sendMessage(ee);

        TextComponent gte = new TextComponent();
        gte.setText(String.format("%d >= %s-%s", data.comparisonVal, data.global, data.local));
        gte.setColor(MessageUtils.EDITING_OPTION);
        gte.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, 1)));
        player.spigot().sendMessage(gte);

        TextComponent gt = new TextComponent();
        gt.setText(String.format("%d > %s-%s", data.comparisonVal, data.global, data.local));
        gt.setColor(MessageUtils.EDITING_OPTION);
        gt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, 2)));
        player.spigot().sendMessage(gt);

        player.sendMessage(MessageUtils.LONG_DASH);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}

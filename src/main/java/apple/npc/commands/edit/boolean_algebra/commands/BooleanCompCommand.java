package apple.npc.commands.edit.boolean_algebra.commands;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.commands.edit.boolean_algebra.data.BooleanDataStore;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarConcluCompDataStore;
import apple.npc.commands.edit.boolean_algebra.reading.comp.ReadBooleanCompVal;
import apple.npc.data.booleanEditing.forced.BooleanEditForced;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BooleanCompCommand implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public BooleanCompCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP));
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
        BooleanVarConcluCompDataStore.put(player.getUniqueId());

        BooleanEditForced exp = BooleanDataStore.get(player.getUniqueId());

        player.sendMessage(MessageUtils.LONG_DASH);
        // deal with starting a session of getting the variable comparison
        BooleanEditForced leftMost = exp.getLeftMost();
        player.sendMessage(exp.toString());
        player.sendMessage(MessageUtils.EDITING_OPTION + String.format("What is the value we're comparing against of exp%d going to be?", leftMost.getName()));

        StopCommand.startListening(new ReadBooleanCompVal(), player);
        player.sendMessage(MessageUtils.LONG_DASH);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
package apple.npc.commands.edit.boolean_algebra.commands;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.edit.boolean_algebra.BooleanSessionStart;
import apple.npc.commands.edit.boolean_algebra.data.BooleanDataStore;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarConcluCompDataStore;
import apple.npc.commands.edit.boolean_algebra.data.VarConcluComparisonObject;
import apple.npc.data.booleanEditing.forced.BooleanEditForced;
import apple.npc.data.booleanEditing.forced.BooleanEditForcedDouble;
import apple.npc.data.booleanEditing.forced.BooleanEditForcedExpBase;
import apple.npc.data.booleanEditing.forced.BooleanEditVarComparison;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BooleanCompTypeCommand implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public BooleanCompTypeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE));
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
            player.sendMessage(MessageUtils.BAD + String.format("an args length of %d is invalid", args.length));
        }
        int type;
        try {
            type = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The first argument must be a number");
            return false;
        }
        VarConcluComparisonObject data = BooleanVarConcluCompDataStore.get(player.getUniqueId());
        data.addComparisonType(type);

        BooleanEditForced exp = BooleanDataStore.get(player.getUniqueId());
        // VarConcluObject has already been gotten
        exp = exp.getLeftMost();
        // set exp to this variable comparison
        BooleanEditForced parent = exp.getParent();
        if (parent == null) {
            BooleanDataStore.put(player.getUniqueId(), new BooleanEditVarComparison(data, null));
        } else {
            // these should be the only possible parents besides no parent
            if (parent instanceof BooleanEditForcedDouble) {
                ((BooleanEditForcedDouble) parent).set(data);
            } else if (parent instanceof BooleanEditForcedExpBase) {
                ((BooleanEditForcedExpBase) parent).set(data);
            }
        }        player.sendMessage(MessageUtils.LONG_DASH);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
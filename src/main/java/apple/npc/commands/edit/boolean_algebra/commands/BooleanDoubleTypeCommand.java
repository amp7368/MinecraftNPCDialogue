package apple.npc.commands.edit.boolean_algebra.commands;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.edit.boolean_algebra.BooleanSessionStart;
import apple.npc.commands.edit.boolean_algebra.data.BooleanDataStore;
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

public class BooleanDoubleTypeCommand implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public BooleanDoubleTypeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS_SPECIFIC_DOUBLE_TYPE);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS_SPECIFIC_DOUBLE_TYPE));
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
        if (args.length != 2) {
            player.sendMessage(MessageUtils.BAD + String.format("an args length of %d is invalid", args.length));
        }
        boolean isAnd;
        boolean isNot;
        try {
            int op = Integer.parseInt(args[0]);
            int not = Integer.parseInt(args[0]);
            if (op == 0) {
                isAnd = false;
            } else if (op == 1) {
                isAnd = true;
            } else
                throw new NumberFormatException("Must be 0 or 1");
            if (not == 0) {
                isNot = false;
            } else if (not == 1) {
                isNot = true;
            } else
                throw new NumberFormatException("Must be 0 or 1");

        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The first argument and second arguments must be 0 or 1");
            return false;
        }
        BooleanDataStore.get(player.getUniqueId());

        BooleanEditForced root = BooleanDataStore.get(player.getUniqueId());
        // VarConcluObject has already been gotten
        BooleanEditForced exp = root.getLeftMost();
        // set exp to this variable comparison
        BooleanEditForced parent = exp.getParent();
        if (parent == null) {
            player.sendMessage("setting root boolean");
            BooleanDataStore.put(player.getUniqueId(), new BooleanEditForcedDouble(isNot, isAnd, null, 0));
        } else {
            player.sendMessage("setting nonRoot boolean");
            // these should be the only possible parents besides no parent
            if (parent instanceof BooleanEditForcedDouble) {
                ((BooleanEditForcedDouble) parent).set(isNot, isAnd, root.getBiggestName());
            } else if (parent instanceof BooleanEditForcedExpBase) {
                ((BooleanEditForcedExpBase) parent).set(isNot, isAnd, root.getBiggestName());
            }
        }
        BooleanSessionStart.step(player);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}

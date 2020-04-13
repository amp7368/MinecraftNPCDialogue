package apple.npc.commands.edit.npc;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllNPCs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EditNpcVarsConcluSpecific implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcVarsConcluSpecific(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS_SPECIFIC);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS_SPECIFIC));
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

        if (args.length != 2) {
            commandSender.sendMessage(String.format(ChatColor.RED + "args length of %d is not valid.", args.length));
            return false;
        }
        int npcUID;
        int conlcusionNum;
        try {
            npcUID = Integer.parseInt(args[0]);
            conlcusionNum = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(MessageUtils.BAD + "The first and second arguments must be a number.");
            return false;
        }
        if (AllNPCs.hasUID(npcUID)) {

        } else {
            player.sendMessage(MessageUtils.BAD + String.format("The npc for uid %d does not exist.", npcUID));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}

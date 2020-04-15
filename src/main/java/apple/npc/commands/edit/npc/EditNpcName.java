package apple.npc.commands.edit.npc;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.npc.NPCData;
import apple.npc.reading.command.npc.edit.ReadingNpcName;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class EditNpcName implements CommandExecutor, TabCompleter {
    public EditNpcName(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_NAME);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_NAME));
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
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        int npcUID;
        try {
            npcUID = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The first argument must be a number.");
            return false;
        }

        NPCData npc = AllNPCs.getNPCFromUID(npcUID);
        if (npc == null) {
            player.sendMessage("There is no Npc with uid of " + npcUID);
            return false;
        }
        player.sendMessage(MessageUtils.LONG_DASH);
        player.sendMessage(MessageUtils.EDITING + "Enter the new name for the npc.");
        player.sendMessage(MessageUtils.LONG_DASH);
        StopCommand.startListening(new ReadingNpcName(npc), player);
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}
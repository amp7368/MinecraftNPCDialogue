package apple.npc.commands.talking;

import apple.npc.data.all.AllNPCs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RespondToNpcCommand implements CommandExecutor {
    JavaPlugin plugin;

    public RespondToNpcCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("npc_respond");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_respond command");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (args.length != 2) {
            commandSender.sendMessage(String.format("%sargs length of %d is not valid.", ChatColor.RED, args.length));
            return true;
        }
        int responseUID;
        int npcUid;
        try {
            npcUid = Integer.parseInt(args[0]);
            responseUID = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED+"The second argument must be a number corresponding to the response number");
            return false;
        }
        AllNPCs.respond(player, npcUid, responseUID);

        return false;
    }
}

package apple.npc.commands.make.npc;

import apple.npc.MessageUtils;
import apple.npc.commands.CreateRedirect;
import apple.npc.data.all.AllNPCs;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MakeNpc implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public MakeNpc(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("npc_make");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_make command");
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }


    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (args.length != 1) {
            player.sendMessage(MessageUtils.BAD + "Invalide number of arguments");
        }
        String npc = args[0];
        AllNPCs.makeNPC(npc, player.getLocation());
        player.sendMessage(MessageUtils.GOOD + String.format("The npc %s has been created.", npc));
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<String> tabComplete = new ArrayList<>();
        return null;
    }
}

package apple.npc.reading.command.npc.make;

import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllNPCs;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReadingMakeNpc extends ReadingCommand {
    private JavaPlugin plugin;

    public ReadingMakeNpc(JavaPlugin pl) {
        this.plugin = pl;
    }

    @Override
    public void dealWithStop(Player player) {
        Bukkit.getScheduler().callSyncMethod(plugin, () -> AllNPCs.makeNPC(super.command, player.getLocation()));

        player.sendMessage(String.format(ChatColor.GREEN + "We made an npc with the name of %s for you..", super.command));
    }
}

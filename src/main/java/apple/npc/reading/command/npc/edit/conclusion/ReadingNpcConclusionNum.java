package apple.npc.reading.command.npc.edit.conclusion;

import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReadingNpcConclusionNum extends ReadingCommand {
    private int npcUID;
    private JavaPlugin plugin;

    public ReadingNpcConclusionNum(int npcUID, JavaPlugin plugin) {
        this.npcUID = npcUID;
        this.plugin = plugin;
    }

    @Override
    public void dealWithStop(Player player) {
        int concluNum;
        try {
            concluNum = Integer.parseInt(super.command);
        } catch (NumberFormatException e) {
            player.sendMessage("finish me");
            StopCommand.startListening(new ReadingNpcConclusionNum(npcUID, plugin), player);
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.performCommand(
                String.format("%s %d %d", CommandReferences.NPC_EDIT_CONCLU_CON_GLOBAL, npcUID, concluNum)));


    }
}


package apple.npc.reading.command.npc.make;

import apple.npc.data.all.AllNPCs;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReadingMakeNpc extends ReadingCommand {
    @Override
    public void dealWithStop(Player player) {
        AllNPCs.makeNPC(super.command, player.getLocation());
        player.sendMessage(String.format(ChatColor.GREEN + "We made an npc with the name of %s for you..", super.command));
    }
}

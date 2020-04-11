package apple.npc.reading.command.npc.edit;

import apple.npc.ColorScheme;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.npc.NPCData;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.entity.Player;

public class ReadingNpcName extends ReadingCommand {
    private NPCData npc;

    public ReadingNpcName(NPCData npc) {
        this.npc = npc;
    }

    @Override
    public void dealWithStop(Player player) {
        npc.setName(command);
        AllNPCs.writeNpc(npc.uid);
        player.sendMessage(ColorScheme.GOOD + " The npc now has the name " + command);
    }
}

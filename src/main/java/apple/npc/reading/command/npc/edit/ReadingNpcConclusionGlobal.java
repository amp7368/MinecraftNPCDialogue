package apple.npc.reading.command.npc.edit;

import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReadingNpcConclusionGlobal extends ReadingCommand {
    private int npcUID;
    private int concluNum;

    public ReadingNpcConclusionGlobal(int npcUID, int concluNum) {
        this.npcUID = npcUID;
        this.concluNum = concluNum;
    }

    @Override
    public void dealWithStop(Player player) {
        if (AllConversations.hasGlobalCategory(super.command)) {
            player.sendMessage(ChatColor.BLUE + "What is the conversation local category (.display to display)");
            StopCommand.startListening(new ReadingNpcConclusionLocal(npcUID, concluNum, super.command), player);
        } else {
            player.sendMessage("finish me correct global");
            StopCommand.startListening(new ReadingNpcConclusionGlobal(npcUID, concluNum), player);
        }
    }
}

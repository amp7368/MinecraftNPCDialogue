package apple.npc.reading.command.npc.edit.conclusion;

import apple.npc.commands.StopCommand;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReadingNpcConclusionNum extends ReadingCommand {
    private int npcUID;

    public ReadingNpcConclusionNum(int npcUID) {
        this.npcUID = npcUID;
    }

    @Override
    public void dealWithStop(Player player) {
        int concluNum;
        try {
            concluNum = Integer.parseInt(super.command);
        } catch (NumberFormatException e) {
            player.sendMessage("finish me");
            StopCommand.startListening(new ReadingNpcConclusionNum(npcUID), player);
            return;
        }
        player.sendMessage(ChatColor.BLUE + "What is the global category of the conversation (.display to display)");
        StopCommand.startListening(new ReadingNpcConclusionGlobal(npcUID, concluNum), player);

    }
}


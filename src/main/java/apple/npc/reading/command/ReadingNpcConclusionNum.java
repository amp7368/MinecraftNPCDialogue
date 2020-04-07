package apple.npc.reading.command;

import apple.npc.commands.StopCommand;
import apple.npc.reading.Reading;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class ReadingNpcConclusionNum extends ReadingCommand {
    private int npcUID;

    public ReadingNpcConclusionNum(int npcUID) {
        this.npcUID = npcUID;
    }

    @Override
    public void dealWithStop(Player player) {
        if (StringUtils.isNumeric(super.command)) {
            int concluNum = Integer.parseInt(super.command);
            player.sendMessage("What is the global category of the conversation (.display to display)");
            StopCommand.startListening(new ReadingNpcConclusionGlobal(npcUID, concluNum), player);
        } else {
            player.sendMessage("finish me");
            StopCommand.startListening(new ReadingNpcConclusionNum(npcUID), player);
        }
    }
}


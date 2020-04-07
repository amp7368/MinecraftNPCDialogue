package apple.npc.reading.command;

import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReadingNpcConclusionLocal extends ReadingCommand {
    private int npcUID;
    private int concluNum;
    private String global;

    public ReadingNpcConclusionLocal(int npcUID, int concluNum, String global) {
        this.npcUID = npcUID;
        this.concluNum = concluNum;
        this.global = global;
    }

    @Override
    public void dealWithStop(Player player) {
        List<Integer> uids = AllConversations.getLocalUIDs(global, super.command);
        if (uids == null) {
            player.sendMessage("finish me");
        } else if (uids.isEmpty()) {
            player.sendMessage("finish me");
            StopCommand.startListening(new ReadingNpcConclusionLocal(npcUID, concluNum, global), player);
        } else if (uids.size() != 1) {
            player.sendMessage("Which local do you want?");
            List<String> uidsString = new ArrayList<>();
            for (int uid : uids) {
                uidsString.add(String.valueOf(uid));
            }
            player.sendMessage(String.join(" | ", uidsString));
        } else {
            player.sendMessage("What is the conversation name");
            StopCommand.startListening(new ReadingNpcConclusionConvo(npcUID, concluNum, global, uids.get(0)), player);
        }
    }
}

package apple.npc.reading.command;

import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllNPCs;
import apple.npc.reading.Reading;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReadingNpcConclusionConvo extends ReadingCommand {
    private int npcUID;
    private int concluNum;
    private String global;
    private int local;

    public ReadingNpcConclusionConvo(int npcUID, int concluNum, String global, int local) {
        this.npcUID = npcUID;
        this.concluNum = concluNum;
        this.global = global;
        this.local = local;
    }

    @Override
    public void dealWithStop(Player player) {
        List<Integer> uids = AllConversations.getConvoUIDs(global, local, super.command);
        if (uids == null) {
            player.sendMessage("finish me");
        } else if (uids.isEmpty()) {
            player.sendMessage("finish me");
            StopCommand.startListening(new ReadingNpcConclusionConvo(npcUID, concluNum, global, local), player);
        } else if (uids.size() != 1) {
            player.sendMessage("Which convo do you want?");
            List<String> uidsString = new ArrayList<>();
            for (int uid : uids) {
                uidsString.add(String.valueOf(uid));
            }
            player.sendMessage(String.join(" | ", uidsString));
        } else {
            AllNPCs.setConcluToConvo(npcUID, concluNum, global, local, uids.get(0));
            player.sendMessage("We just updated that convo for " + npcUID);
        }
    }
}

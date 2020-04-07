package apple.npc.commands;

import apple.npc.data.all.AllNPCs;
import apple.npc.reading.command.ReadingNpcName;
import org.bukkit.entity.Player;

import java.util.List;

public class EditRedirect {
    public static void editNpc(Player player) {
        player.sendMessage("Which npc do you want to edit? ('.display' to display a list of npc names)");
        StopCommand.startListening(new ReadingNpcName(),player);
    }

    public static void editNpcConcluToConvo(String npcName, Player player) {
        List<Integer> uids = AllNPCs.getNpcUids(npcName);
        if (uids.isEmpty()) {
            player.sendMessage(String.format("There is no %s npc", npcName));
            return;
        }
        if (uids.size() != 1) {
            player.sendMessage(String.format("There are %d npc(s) named %s", uids.size(), npcName));
            return;
        }
        int uid = uids.get(0);
//        StopCommand.startListening(new Reading(), player);

    }

}

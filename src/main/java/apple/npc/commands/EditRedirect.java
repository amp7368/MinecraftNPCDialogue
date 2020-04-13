package apple.npc.commands;

import apple.npc.data.all.AllNPCs;
import apple.npc.reading.command.npc.edit.conclusion.ReadingNpcConclusionNum;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * a class that deals with all the edit redirects
 */
public class EditRedirect {

    /**
     * starts a session editing the conclusionToConversation in the npc
     *
     * @param npcUID the uid of the npc we're editing
     * @param player the player requesting this service
     */
    public static void editNpcConcluToConvo(int npcUID, Player player) {
        if (AllNPCs.hasUID(npcUID)) {
            player.sendMessage(ChatColor.BLUE + "What conclusion would you like to set? ('.display' to display a list of conclusions)");
            StopCommand.startListening(new ReadingNpcConclusionNum(npcUID), player);
        } else {
            player.sendMessage(ChatColor.RED + "There is no npc with uid " + npcUID);
        }
//        StopCommand.startListening(new Reading(), player);
    }

}

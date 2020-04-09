package apple.npc.reading.text;

import apple.npc.data.all.AllConversations;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReadingTextConvo extends ReadingText {
    private String global;
    private int local;
    private String convo;


    public ReadingTextConvo(String global, int local, String convoUID) {
        this.global = global;
        this.local = local;
        this.convo = convoUID;
    }

    @Override
    public void dealWithStop(Player player) {
        if (AllConversations.createConvo(global, local, convo, super.text)) {
            player.sendMessage(String.format(ChatColor.RED + "There now exists a conversation in %s:%s:%s", global, AllConversations.getLocalName(global, local), convo));
            for (String string : super.text)
                player.sendMessage(string);
        } else {
            player.sendMessage(String.format(ChatColor.RED + "I could not deal with making a conversation in %s:%s:%s", global, AllConversations.getLocalName(global, local), convo));
        }
    }
}

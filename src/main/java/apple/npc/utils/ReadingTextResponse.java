package apple.npc.utils;

import apple.npc.data.all.AllConversations;
import apple.npc.data.reference.ConvoID;
import org.bukkit.entity.Player;

public class ReadingTextResponse extends ReadingText {
    private String global;
    private int local;
    private int convo;


    public ReadingTextResponse(String global, int local, int convoUID) {
        this.global = global;
        this.local = local;
        this.convo = convoUID;
    }

    @Override
    public void dealWithStop(Player player) {
        if (AllConversations.createResponse(global, local, convo, super.text)) {
            //todo deal with vvv
            player.sendMessage(String.format("There now exists an option in %s:%s:%s", global, AllConversations.getLocalName(global, local), AllConversations.get(new ConvoID(global, local, convo)).name));
            for (String string : super.text)
                player.sendMessage(string);
        } else {
            player.sendMessage(String.format("I could not deal with making a conversation in %s:%s:%s", global, AllConversations.getLocalName(global, local), convo));
        }
    }
}

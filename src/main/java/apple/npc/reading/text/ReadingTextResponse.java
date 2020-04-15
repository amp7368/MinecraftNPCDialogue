package apple.npc.reading.text;

import apple.npc.MessageUtils;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConvoID;
import org.bukkit.ChatColor;
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
        player.sendMessage(MessageUtils.LONG_DASH);
        if (AllConversations.createResponse(global, local, convo, super.text)) {
            //todo deal with vvv
            player.sendMessage(String.format(MessageUtils.GOOD + "There now exists a new response in %s:%s:%s", global, AllConversations.getLocalName(global, local), AllConversations.get(new ConvoID(global, local, convo)).name));
            for (String string : super.text)
                player.sendMessage(string);
        } else {
            ConversationData convoObject = AllConversations.get(new ConvoID(global, local, convo));
            if (convoObject == null) {
                player.sendMessage(String.format("I could not get conversation %s:%d:%d", global, local, convo));
            } else
                player.sendMessage(String.format(ChatColor.RED + "I could not deal with making a response in %s:%s:%s",
                        global, AllConversations.getLocalName(global, local), convoObject.name));
        }
        player.sendMessage(MessageUtils.LONG_DASH);

    }
}

package apple.npc.reading.command.response;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.*;
import apple.npc.reading.command.ReadingCommand;
import apple.npc.reading.command.ResponseType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReadingConvoResponseConvo extends ReadingCommand {
    private String global;
    private int local;
    private int convo;
    private int response;
    private ResponseType type;
    private String newGlobal;
    private int newLocal;

    public ReadingConvoResponseConvo(String global, int local, int convo, int response, ResponseType type, String newGlobal, int newLocal, Player player) {
        this.global = global;
        this.local = local;
        this.convo = convo;
        this.response = response;
        this.type = type;
        this.newGlobal = newGlobal;
        this.newLocal = newLocal;

        player.sendMessage(MessageUtils.LONG_DASH);

        TextComponent welcome = new TextComponent();
        welcome.setText("What conversation would you like it to refer to?");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        player.spigot().sendMessage(welcome);

        ConversationLocalCategory localCat = AllConversations.getLocalCategory(newGlobal, newLocal);
        if (localCat == null) {
            //todo deal with this
            System.out.println("localCat is null");
            return;
        }

        for (ConversationData conversation : localCat.getConversations().values()) {
            player.sendMessage(MessageUtils.DASH);

            TextComponent category = new TextComponent();
            category.setText(String.format("(%s)", conversation.name));
            category.setUnderlined(true);
            category.setColor(MessageUtils.EDITING_OPTION);
            category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d %s %d %d",
                    CommandReferences.NPC_CONVO_EDIT_RESPONSE_SET, global, local, convo, response, newGlobal, newLocal, conversation.uid)));
            player.spigot().sendMessage(category);
        }
        player.sendMessage(MessageUtils.DASH);
        StopCommand.showAbort(player);
        player.sendMessage(MessageUtils.LONG_DASH);
    }

    @Override
    public void dealWithStop(Player player) {
        List<Integer> uids = AllConversations.getConvoUIDs(newGlobal, newLocal, command);
        if (uids == null) {
            player.sendMessage("finish me");
        } else if (uids.isEmpty()) {
            player.sendMessage("finish me");
            StopCommand.startListening(new ReadingConvoResponseConvo(global, local, convo, response, type, newGlobal, newLocal, player), player);
        } else if (uids.size() != 1) {
            player.sendMessage(ChatColor.BLUE + "Which convo do you want?");
            List<String> uidsString = new ArrayList<>();
            for (int uid : uids) {
                uidsString.add(String.valueOf(uid));
            }
            player.sendMessage(String.join(" | ", uidsString));
        } else {
            int newConvoUID = uids.get(0);
            ConversationData conversation = AllConversations.get(new ConvoID(global, local, convo));
            conversation.get(response).getPostResponses().set(0, new PostPlayerResponse(newGlobal, newLocal, newConvoUID));
            AllConversations.writeAll();

//            StopCommand.startListening(new ReadingBoolean(global, local, convo, response, type, newGlobal, newLocal, newConvoUID, player), player);
        }
    }
}

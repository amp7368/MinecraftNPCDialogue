package apple.npc.reading.command.response;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationLocalCategory;
import apple.npc.reading.command.ReadingCommand;
import apple.npc.reading.command.ResponseType;
import apple.npc.reading.command.npc.edit.ReadingNpcConclusionLocal;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReadingConvoResponseLocal extends ReadingCommand {
    private String global;
    private int local;
    private int convo;
    private int response;
    private ResponseType type;
    private String newGlobal;
    private Player player;

    public ReadingConvoResponseLocal(String global, int local, int convo, int response, ResponseType type, String newGlobal, Player player) {
        this.global = global;
        this.local = local;
        this.convo = convo;
        this.response = response;
        this.type = type;
        this.newGlobal = newGlobal;
        this.player = player;


        player.sendMessage(MessageUtils.LONG_DASH);

        TextComponent welcome = new TextComponent();
        welcome.setText("What conversation local category would you like it to refer to?");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        player.spigot().sendMessage(welcome);

        Collection<ConversationLocalCategory> localNames = AllConversations.getLocalList(newGlobal);
        if (localNames == null) {
            //todo deal with this
            return;
        }

        for (ConversationLocalCategory localName : localNames) {
            player.sendMessage(MessageUtils.DASH);

            TextComponent category = new TextComponent();
            category.setText(String.format("(%s)", localName.getName()));
            category.setUnderlined(true);
            category.setColor(MessageUtils.EDITING_OPTION);
            category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d %s %d",
                    CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST_CONVO, global, local, convo, response, newGlobal, localName.getUid())));
            player.spigot().sendMessage(category);
        }
        player.sendMessage(MessageUtils.DASH);
        StopCommand.showAbort(player);
        player.sendMessage(MessageUtils.LONG_DASH);
    }

    @Override
    public void dealWithStop(Player player) {
        List<Integer> uids = AllConversations.getLocalUIDs(newGlobal, command);
        if (uids == null) {
            player.sendMessage("finish me");
        } else if (uids.isEmpty()) {
            player.sendMessage("finish me");
            StopCommand.startListening(new ReadingConvoResponseLocal(global, local, convo, response, type, newGlobal, player), player);
        } else if (uids.size() != 1) {
            player.sendMessage(ChatColor.BLUE + "Which local do you want?");
            List<String> uidsString = new ArrayList<>();
            for (int uid : uids) {
                uidsString.add(String.valueOf(uid));
            }
            player.sendMessage(String.join(" | ", uidsString));
        } else {
            int newLocalUID = uids.get(0);
            StopCommand.startListening(new ReadingConvoResponseConvo(global, local, convo, response, type, newGlobal, newLocalUID, player), player);
        }
    }
}

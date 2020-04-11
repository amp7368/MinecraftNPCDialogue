package apple.npc.reading.command.response;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.reading.command.ReadingCommand;
import apple.npc.reading.command.ResponseType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ReadingConvoResponseGlobal extends ReadingCommand {

    private String global;
    private int local;
    private int convo;
    private int response;
    private ResponseType type; // 0 = Normal

    public ReadingConvoResponseGlobal(String global, int local, int convo, int response, ResponseType type, Player player) {
        this.global = global;
        this.local = local;
        this.convo = convo;
        this.response = response;
        this.type = type;

        player.sendMessage(MessageUtils.LONG_DASH);

        TextComponent welcome = new TextComponent();
        welcome.setText("What conversation global category would you like it to refer to?");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);

        Collection<String> globalNames = AllConversations.getList();
        for (String globalName : globalNames) {
            player.sendMessage(MessageUtils.DASH);

            TextComponent category = new TextComponent();
            category.setText(String.format("(Edit %s)", globalName));
            category.setUnderlined(true);
            category.setColor(MessageUtils.EDITING_OPTION);
            category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d %s",
                    CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST_LOCAL, global, local, convo, response, globalName)));
            player.spigot().sendMessage(category);
        }
        player.sendMessage(MessageUtils.DASH);
        StopCommand.showAbort(player);
        player.sendMessage(MessageUtils.LONG_DASH);
    }

    @Override
    public void dealWithStop(Player player) {
        StopCommand.startListening(new ReadingConvoResponseLocal(global, local, convo, response, type, command,player), player);
    }
}

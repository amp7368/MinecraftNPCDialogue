package apple.npc.reading.command.convo;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllConversations;
import apple.npc.reading.command.ReadingCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class ReadingConvoLocal extends ReadingCommand {
    private String global;

    public ReadingConvoLocal(String global) {
        this.global = global;
    }

    @Override
    public void dealWithStop(Player player) {
        String local = command;
        if (AllConversations.hasLocalCategory(global, local)) {
            List<Integer> localUIDs = AllConversations.getLocalUIDs(global, local);
            if (localUIDs == null) {
                System.err.println(String.format("%s:%s did not exist in AllConversations after we just checked it", global, local));
                return;
            }
            for (Integer localUID : localUIDs) {
                player.sendMessage(MessageUtils.DASH);

                TextComponent category = new TextComponent();
                category.setText(String.format("(Edit %s:%s (UID:%d)", global, local, localUID));
                category.setUnderlined(true);
                category.setColor(MessageUtils.EDITING_OPTION);
                category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d", CommandReferences.NPC_CONVO_EDIT_CONVO, global, localUID)));


                TextComponent editName = new TextComponent();
                editName.setText("(Edit the name");
                editName.setUnderlined(true);
                editName.setColor(MessageUtils.EDITING_OPTION);
                editName.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d", CommandReferences.NPC_CONVO_EDIT_LOCAL_NAME, global, localUID)));

                player.spigot().sendMessage(category);
                player.spigot().sendMessage(editName);
            }
            player.sendMessage(MessageUtils.DASH);

        } else {
            TextComponent yes = new TextComponent();
            yes.setText("(Yes)");
            yes.setUnderlined(true);
            yes.setColor(MessageUtils.EDITING_OPTION);
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %s", CommandReferences.NPC_CONVO_MAKE_LOCAL, global, local)));


            TextComponent no = new TextComponent();
            no.setText("(No)");
            no.setUnderlined(true);
            no.setColor(MessageUtils.EDITING_OPTION);
            no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s", CommandReferences.NPC_CONVO_EDIT_LOCAL, global)));

            TextComponent separator = new TextComponent();
            separator.setText("   ");
            player.sendMessage(MessageUtils.EDITING + String.format("The local category '%s:%s' doesn't exist. Would you like to create it?", global, local));
            player.spigot().sendMessage(yes, separator, no);
        }
        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(MessageUtils.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s", CommandReferences.NPC_CONVO_EDIT_LOCAL, global)));
        player.spigot().sendMessage(back);
        player.sendMessage("");

    }
}

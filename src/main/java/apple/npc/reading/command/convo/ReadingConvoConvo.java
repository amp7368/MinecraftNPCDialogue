package apple.npc.reading.command.convo;

import apple.npc.ColorScheme;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllConversations;
import apple.npc.reading.command.ReadingCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class ReadingConvoConvo extends ReadingCommand {
    private String global;
    private int local;

    public ReadingConvoConvo(String global, int local) {
        this.global = global;
        this.local = local;
    }

    @Override
    public void dealWithStop(Player player) {
        String convo = command;
        List<Integer> convoUIDs = AllConversations.getConvoUIDs(global, local, convo);
        String localName = AllConversations.getLocalName(global, local);
        if (localName == null) {
            player.sendMessage(ColorScheme.BAD + "There is no local category with that uid.");
            return;
        }
        if (convoUIDs == null) {
            player.sendMessage(ColorScheme.BAD + "There is no conversation with that category.");
            return;
        } else if (!convoUIDs.isEmpty()) {
            for (Integer convoUID : convoUIDs) {
                player.sendMessage(ColorScheme.DASH);

                TextComponent category = new TextComponent();

                category.setText(String.format("(Edit Responses %s:%s:%s (UID:%d))", global, localName, convo, convoUID));
                category.setUnderlined(true);
                category.setColor(ColorScheme.EDITING_OPTION);
                category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d",
                        CommandReferences.NPC_CONVO_EDIT_RESPONSE, global, local, convoUID)));


                TextComponent editName = new TextComponent();
                editName.setText("(Edit the name)");
                editName.setUnderlined(true);
                editName.setColor(ColorScheme.EDITING_OPTION);
                editName.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d",
                        CommandReferences.NPC_CONVO_EDIT_CONVO_NAME, global, local, convoUID)));

                TextComponent conversationText = new TextComponent();
                conversationText.setText("(Edit the text)");
                conversationText.setUnderlined(true);
                conversationText.setColor(ColorScheme.EDITING_OPTION);
                conversationText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d",
                        CommandReferences.NPC_CONVO_EDIT_CONVO_TEXT, global, local, convoUID)));

                TextComponent immediateConvo = new TextComponent();
                immediateConvo.setText("(Edit the immediate convo)");
                immediateConvo.setUnderlined(true);
                immediateConvo.setColor(ColorScheme.EDITING_OPTION);
                immediateConvo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d",
                        CommandReferences.NPC_CONVO_EDIT_CONVO_IMM, global, local, convoUID)));

                player.spigot().sendMessage(category);
                player.spigot().sendMessage(editName);
                player.spigot().sendMessage(conversationText);
                player.spigot().sendMessage(immediateConvo);
            }
            player.sendMessage(ColorScheme.DASH);

        } else {
            TextComponent yes = new TextComponent();
            yes.setText("(Yes)");
            yes.setUnderlined(true);
            yes.setColor(ColorScheme.EDITING_OPTION);
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %s", CommandReferences.NPC_CONVO_MAKE_CONVO, global, local, convo)));


            TextComponent no = new TextComponent();
            no.setText("(No)");
            no.setUnderlined(true);
            no.setColor(ColorScheme.EDITING_OPTION);
            no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d", CommandReferences.NPC_CONVO_EDIT_CONVO, global, local)));

            TextComponent separator = new TextComponent();
            separator.setText("   ");
            player.sendMessage(ColorScheme.EDITING + String.format("The conversation '%s:%s:%s' doesn't exist. Would you like to create it?", global, localName, convo));
            player.spigot().sendMessage(yes, separator, no);
        }
        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(ColorScheme.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d", CommandReferences.NPC_CONVO_EDIT_CONVO, global, local)));
        player.spigot().sendMessage(back);
        player.sendMessage("");

    }
}


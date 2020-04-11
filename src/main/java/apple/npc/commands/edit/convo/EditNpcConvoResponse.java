package apple.npc.commands.edit.convo;

import apple.npc.ColorScheme;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.ConvoID;
import apple.npc.reading.command.convo.ReadingConvoConvo;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EditNpcConvoResponse implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoResponse(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE));
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (args.length != 3) {
            player.sendMessage(ColorScheme.BAD + "Invalid number of arguments");
            return false;
        }
        String global = args[0];
        int local;
        int convo;
        try {
            local = Integer.parseInt(args[1]);
            convo = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage(ColorScheme.BAD + "The second and third argument must be a number.");
            return false;
        }
        player.sendMessage(ColorScheme.LONG_DASH);
        ConversationData conversation = AllConversations.get(new ConvoID(global, local, convo));
        if (conversation == null) {
            player.sendMessage(ColorScheme.BAD + String.format("%s:%d:%d is an invalid conversation", global, local, convo));
            return false;
        }

        for (ConversationResponse response : conversation.responses) {
            player.sendMessage(ColorScheme.DASH);

            TextComponent category = new TextComponent();
            category.setText(String.format("(Edit %s:%s:%s:%d", global, local, convo, response.uid));
            category.setUnderlined(true);
            category.setColor(ColorScheme.EDITING_OPTION);
            category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d", CommandReferences.NPC_CONVO_EDIT_RESPONSE_DETAILS, global, local, convo, response.uid)));


            player.spigot().sendMessage(category);
            for (String text : response.response) {
                player.sendMessage(text);
            }
        }
        player.sendMessage(ColorScheme.DASH);
        TextComponent make = new TextComponent();
        make.setText("(Make Response");
        make.setUnderlined(true);
        make.setColor(ColorScheme.EDITING_OPTION);
        make.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d",
                CommandReferences.NPC_CONVO_MAKE_RESPONSE, global, local, convo)));


        player.spigot().sendMessage(make);


        player.sendMessage(ColorScheme.DASH);
        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(ColorScheme.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d", CommandReferences.NPC_CONVO_EDIT_CONVO, global, local)));
        player.spigot().sendMessage(back);
        player.sendMessage(ColorScheme.LONG_DASH);


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
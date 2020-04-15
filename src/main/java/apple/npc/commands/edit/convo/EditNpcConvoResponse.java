package apple.npc.commands.edit.convo;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.ConvoID;
import apple.npc.reading.text.ReadingTextResponse;
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
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        String global = args[0];
        int local;
        int convo;
        try {
            local = Integer.parseInt(args[1]);
            convo = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The second and third argument must be a number.");
            return false;
        }


        player.sendMessage(MessageUtils.LONG_DASH);
        ConversationData conversation = AllConversations.get(new ConvoID(global, local, convo));
        if (conversation == null) {
            player.sendMessage(MessageUtils.BAD + String.format("%s:%d:%d is an invalid conversation", global, local, convo));
            return false;
        }

        String localName = AllConversations.getLocalName(global, local);
        String convoName = conversation.name;

        if (localName == null) {
            player.sendMessage(String.format("The local category %s:%d does not exist", global, local));
            return false;
        }
        TextComponent welcome = new TextComponent();
        welcome.setText("What response id you would you like to edit?");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        player.spigot().sendMessage(welcome);


        for (ConversationResponse response : conversation.responses) {
            player.sendMessage(MessageUtils.DASH);

            TextComponent category = new TextComponent();
            category.setText(String.format("(Edit %s:%s:%s:%d)", global, localName, convoName, response.uid));
            category.setUnderlined(true);
            category.setColor(MessageUtils.EDITING_OPTION);
            category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d", CommandReferences.NPC_CONVO_EDIT_RESPONSE_DETAILS, global, local, convo, response.uid)));


            player.spigot().sendMessage(category);
            for (String text : response.response) {
                player.sendMessage(MessageUtils.TAB + text);
            }
        }

        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(MessageUtils.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d",
                CommandReferences.NPC_CONVO_EDIT_CONVO, global, local)));
        player.sendMessage("");
        player.spigot().sendMessage(back);

        StopCommand.startListening(new ReadingTextResponse(global,local,convo), player);

        player.sendMessage(MessageUtils.DASH);
        player.sendMessage(MessageUtils.LONG_DASH);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
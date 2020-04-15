package apple.npc.commands.edit.convo;

import apple.npc.ActionBar;
import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConvoID;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EditNpcConvoResponseDetails implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoResponseDetails(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_DETAILS);
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the " + CommandReferences.NPC_CONVO_EDIT_RESPONSE_DETAILS + " command");
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

        if (args.length != 4) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        String global = args[0];
        int localUID;
        int convoUID;
        int responseUID;
        try {
            localUID = Integer.parseInt(args[1]);
            convoUID = Integer.parseInt(args[2]);
            responseUID = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The second, third, and fourth arguments must be a number.");
            return false;
        }
        ConversationData conversation = AllConversations.get(new ConvoID(global, localUID, convoUID));
        String localName = AllConversations.getLocalName(global, localUID);
        if (conversation == null || localName == null) {
            player.sendMessage(MessageUtils.BAD + String.format("%s:%d:%d is an invalid conversation", global, localUID, convoUID));
            return false;
        }
        TextComponent path = new TextComponent();
        path.setText(String.format("Convo | Global-Local-Convo-Response | %s-%s-%s-%d", global, localName, conversation.name, responseUID));
        path.setBold(MessageUtils.PATH_BOLD);
        path.setColor(MessageUtils.PATH);
        ActionBar.sendLongActionBar(player, path);

        player.sendMessage(MessageUtils.LONG_DASH);

        TextComponent welcome = new TextComponent();
        welcome.setText(String.format("What would you like to edit about response %s:%s:%s:%d?   ",
                global, localName, conversation.name, responseUID));
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);

        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d",
                CommandReferences.NPC_CONVO_EDIT_RESPONSE, global, localUID, convoUID)));

        TextComponent preResponse = new TextComponent();
        preResponse.setText("(PreResponseRequirement)");
        preResponse.setUnderlined(true);
        preResponse.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        preResponse.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d",
                CommandReferences.NPC_CONVO_EDIT_RESPONSE_PRERESPONSE, global, localUID, convoUID, responseUID)));

        TextComponent text = new TextComponent();
        text.setText("(Text)");
        text.setUnderlined(true);
        text.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d",
                CommandReferences.NPC_CONVO_EDIT_RESPONSE_TEXT, global, localUID, convoUID, responseUID)));

        TextComponent postResponse = new TextComponent();
        postResponse.setText("(PostResponses)");
        postResponse.setUnderlined(true);
        postResponse.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        postResponse.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d",
                CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST, global, localUID, convoUID, responseUID)));

        TextComponent postResponseMake = new TextComponent();
        postResponseMake.setText("(Make PostResponse)");
        postResponseMake.setUnderlined(true);
        postResponseMake.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        postResponseMake.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d",
                CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST_MAKE, global, localUID, convoUID, responseUID)));

        TextComponent defaultResponse = new TextComponent();
        defaultResponse.setText("(DefaultPostResponse)");
        defaultResponse.setUnderlined(true);
        defaultResponse.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        defaultResponse.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d",
                CommandReferences.NPC_CONVO_EDIT_RESPONSE_DEFAULT, global, localUID, convoUID, responseUID)));

        player.sendMessage(MessageUtils.LONG_DASH);
        player.spigot().sendMessage(welcome);
        player.spigot().sendMessage(preResponse);
        player.spigot().sendMessage(text);
        player.spigot().sendMessage(postResponse);
        player.spigot().sendMessage(postResponseMake);
        player.spigot().sendMessage(defaultResponse);
        player.sendMessage("");
        player.spigot().sendMessage(back);

        player.sendMessage(MessageUtils.LONG_DASH);
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<String> tabComplete = new ArrayList<>();
        return null;
    }
}

package apple.npc.commands.edit.convo.detail.resp;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.ConvoID;
import apple.npc.data.convo.PostPlayerResponse;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EditNpcConvoResponsePost implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoResponsePost(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST));
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

        String localName = AllConversations.getLocalName(global, localUID);
        if (localName == null) {
            player.sendMessage(MessageUtils.BAD + String.format("I could not get local category %s:%d", global, localUID));
            return false;
        }
        ConversationData convo = AllConversations.get(new ConvoID(global, localUID, convoUID));
        if (convo == null) {
            player.sendMessage(MessageUtils.BAD + String.format("I could not get conversation %s:%s:%d", global, localName, convoUID));
            return false;
        }
        ConversationResponse response = convo.responses.get(responseUID);
        if (response == null) {
            player.sendMessage(MessageUtils.BAD + String.format("I could not get response %s-%s-%s-%d", global, localName, convo.name, responseUID));
            return false;
        }

        player.sendMessage(MessageUtils.LONG_DASH);
        player.sendMessage(MessageUtils.EDITING + "Editing PostResponses");
        List<PostPlayerResponse> redirects = response.getPostResponses();
        int i = 0;
        for (PostPlayerResponse redirect : redirects) {
            TextComponent varChange = new TextComponent();
            varChange.setText(String.format("(Edit Variable Changes %s-%d-%d)", redirect.getResponseGlobal(), redirect.getResponseLocal(), redirect.getConversationUID()));
            varChange.setColor(net.md_5.bungee.api.ChatColor.GREEN);
            varChange.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d %d",
                    CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_GLOBAL, global, localUID, convoUID, responseUID, i++)));
            player.spigot().sendMessage(varChange);

            TextComponent redirectReqs = new TextComponent();
            redirectReqs.setText(String.format("(Edit Redirect Reqs %s-%d-%d)", redirect.getResponseGlobal(), redirect.getResponseLocal(), redirect.getConversationUID()));
            redirectReqs.setColor(net.md_5.bungee.api.ChatColor.GREEN);
            redirectReqs.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d %d",
                    CommandReferences.NPC_CONVO_EDIT_RESPONSE_REQS, global, localUID, convoUID, responseUID, i++)));
            player.spigot().sendMessage(redirectReqs);

            TextComponent delete = new TextComponent();
            delete.setText(String.format("(Delete %s-%d-%d)", redirect.getResponseGlobal(), redirect.getResponseLocal(), redirect.getConversationUID()));
            delete.setColor(net.md_5.bungee.api.ChatColor.GREEN);
            delete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d %d %d %d",
                    CommandReferences.NPC_CONVO_DELETE_POST_RESPONSE, global, localUID, convoUID, responseUID, i++)));
            player.spigot().sendMessage(delete);
        }
        player.sendMessage(MessageUtils.LONG_DASH);


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
package apple.npc.commands.edit.convo.detail.resp.making;

import apple.npc.ActionBar;
import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConvoID;
import apple.npc.data.convo.PostPlayerResponse;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EditnpcConvoResponseSet implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditnpcConvoResponseSet(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_SET);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE_SET));
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
        String global;
        int local;
        int convo;
        int response;
        String newGlobal;
        int newLocal;
        int newConvoUID;
        if (args.length != 7) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of args");
            return false;
        }
        global = args[0];
        try {
            local = Integer.parseInt(args[1]);
            convo = Integer.parseInt(args[2]);
            response = Integer.parseInt(args[3]);
            newLocal = Integer.parseInt(args[5]);
            newConvoUID = Integer.parseInt(args[6]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The second, third, and fourth argument must be numbers");
            return false;
        }
        newGlobal = args[4];
        String localName = AllConversations.getLocalName(global, local);
        if (localName == null) {
            player.sendMessage(MessageUtils.BAD + String.format("I could not get local category %s:%d", global, local));
            return false;
        }
        ConversationData conversation = AllConversations.get(new ConvoID(global, local, convo));
        if (conversation == null) {
            player.sendMessage(MessageUtils.BAD + String.format("The conversation %s:%d:%d does not exist.", global, local, convo));
            return false;
        } else {
            ConversationData newConversation = AllConversations.get(new ConvoID(newGlobal, newLocal, newConvoUID));
            if (newConversation == null) {
                player.sendMessage(MessageUtils.BAD + String.format("The new conversation %s:%d:%d does not exist.", newGlobal, newLocal, newConvoUID));
                return false;
            }
            TextComponent path = new TextComponent();
            path.setText(String.format("Convo | Global-Local-Convo-Response-newGlobal-newLocal-newConvo | %s-%s-%s-%d-%s-%s-%s", global, localName, conversation.name, response, newGlobal, newLocal, newConversation.name));
            path.setBold(MessageUtils.PATH_BOLD);
            path.setColor(MessageUtils.PATH);
            ActionBar.sendLongActionBar(player, path);
            List<PostPlayerResponse> resp = conversation.get(response).getPostResponses();
            boolean isAlreadyResponse = false;
            int i = 0;
            for (PostPlayerResponse post : resp) {
                if (post.equals(newGlobal, newLocal, newConvoUID)) {
                    isAlreadyResponse = true;
                    break;
                }
                i++;
            }

            if (isAlreadyResponse)
                resp.set(i, new PostPlayerResponse(newGlobal, newLocal, newConvoUID));
            else
                resp.add(new PostPlayerResponse(newGlobal, newLocal, newConvoUID));
            AllConversations.writeAll();
            player.sendMessage("You just set the thing");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
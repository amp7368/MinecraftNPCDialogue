package apple.npc.commands.edit.convo.detail.resp;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConvoID;
import apple.npc.reading.command.ResponseType;
import apple.npc.reading.command.response.ReadingConvoResponseGlobal;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EditNpcConvoResponsePostMake implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoResponsePostMake(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST_MAKE);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST_MAKE));
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
        String convoName = convo.name;


        player.sendMessage(MessageUtils.LONG_DASH);
        player.sendMessage(MessageUtils.EDITING + String.format("Starting to edit the normal conversation redirect for %s:%s:%s:%d",
                global, localName, convoName, responseUID));
        player.sendMessage(MessageUtils.LONG_DASH);

        StopCommand.startListening(new ReadingConvoResponseGlobal(global, localUID, convoUID, responseUID, ResponseType.NORMAL_POST_RESPONSE, player), player);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

package apple.npc.commands.edit.convo.detail.resp.making;

import apple.npc.ActionBar;
import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConvoID;
import apple.npc.reading.command.ResponseType;
import apple.npc.reading.command.response.ReadingConvoResponseGlobal;
import apple.npc.reading.command.response.ReadingConvoResponseLocal;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.xml.ws.Response;
import java.util.List;

public class EditNpcConvoResponseLocal implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoResponseLocal(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST_LOCAL);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST_LOCAL));
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

        if (args.length != 5) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of args");
            return false;
        }
        global = args[0];
        try {
            local = Integer.parseInt(args[1]);
            convo = Integer.parseInt(args[2]);
            response = Integer.parseInt(args[3]);
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
        ConversationData conversationData = AllConversations.get(new ConvoID(global, local, convo));
        if (conversationData == null) {
            player.sendMessage(MessageUtils.BAD + String.format("I could not get conversation %s:%s:%d", global, localName, convo));
            return false;
        }

        TextComponent path = new TextComponent();
        path.setText(String.format("Convo | Global-Local-Convo-Response-newGlobal-(newLocal) | %s-%s-%s-%d-%s", global, localName, conversationData.name, response, newGlobal));
        path.setBold(MessageUtils.PATH_BOLD);
        path.setColor(MessageUtils.PATH);
        ActionBar.sendLongActionBar(player, path);


        StopCommand.startListening(new ReadingConvoResponseLocal(global, local, convo, response, ResponseType.NORMAL_POST_RESPONSE, newGlobal, player), player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

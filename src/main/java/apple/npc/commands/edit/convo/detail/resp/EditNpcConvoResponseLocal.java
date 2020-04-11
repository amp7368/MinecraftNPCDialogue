package apple.npc.commands.edit.convo.detail.resp;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.reading.command.ResponseType;
import apple.npc.reading.command.response.ReadingConvoResponseGlobal;
import apple.npc.reading.command.response.ReadingConvoResponseLocal;
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

        StopCommand.startListening(new ReadingConvoResponseLocal(global, local, convo, response, ResponseType.NORMAL_POST_RESPONSE, newGlobal, player), player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

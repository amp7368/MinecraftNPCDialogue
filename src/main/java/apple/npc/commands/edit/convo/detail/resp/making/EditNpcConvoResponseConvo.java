package apple.npc.commands.edit.convo.detail.resp.making;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.reading.command.ResponseType;
import apple.npc.reading.command.response.ReadingConvoResponseConvo;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EditNpcConvoResponseConvo implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoResponseConvo(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST_CONVO);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE_POST_CONVO));
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
        if (args.length != 6) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of args");
            return false;
        }
        global = args[0];
        try {
            local = Integer.parseInt(args[1]);
            convo = Integer.parseInt(args[2]);
            response = Integer.parseInt(args[3]);
            newLocal = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The second, third, and fourth argument must be numbers");
            return false;
        }
        newGlobal = args[4];

        StopCommand.startListening(new ReadingConvoResponseConvo(global, local, convo, response, ResponseType.NORMAL_POST_RESPONSE, newGlobal, newLocal, player), player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

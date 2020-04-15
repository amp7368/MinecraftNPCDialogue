package apple.npc.commands.make.convo;

import apple.npc.MessageUtils;
import apple.npc.commands.CreateRedirect;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * a class for creating a conversation given a global, local, and the conversation name that is requested
 */
public class MakeCon implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public MakeCon(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("npc_convo_make_convo");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_convo_make_convo command");
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
            commandSender.sendMessage(String.format(ChatColor.RED + "args length of %d is not valid.", args.length));
            return true;
        }
        int local;
        try {
            local = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            CreateRedirect.createConvo(args[0], args[1], args[2], player);
            return true;
        }
        TextComponent path = new TextComponent();
        path.setText(String.format("Conversation Creation-%s-%s",args[0],local));
        path.setBold(MessageUtils.PATH_BOLD);
        path.setColor(MessageUtils.PATH);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,path);

        CreateRedirect.createConvo(args[0], local, args[2], player);

        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<String> tabComplete = new ArrayList<>();
        return null;
    }
}

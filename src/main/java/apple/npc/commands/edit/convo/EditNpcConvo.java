package apple.npc.commands.edit.convo;

import apple.npc.commands.CommandReferences;
import apple.npc.commands.CreateRedirect;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EditNpcConvo implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvo(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT);
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the " + CommandReferences.NPC_CONVO_EDIT + " command");
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
        TextComponent welcome = new TextComponent();
        welcome.setText("What would you like to edit about conversations?   ");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);

        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + CommandReferences.NPC));

        TextComponent global = new TextComponent();
        global.setText("(Global Categories)");
        global.setUnderlined(true);
        global.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        global.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + CommandReferences.NPC_CONVO_EDIT_GLOBAL));
        TextComponent separator = new TextComponent();
        separator.setText("   ");

        player.spigot().sendMessage(welcome);
        player.spigot().sendMessage(global);
        player.spigot().sendMessage(back);
        player.sendMessage("");
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<String> tabComplete = new ArrayList<>();
        return null;
    }
}

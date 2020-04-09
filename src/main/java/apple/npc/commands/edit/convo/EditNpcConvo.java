package apple.npc.commands.edit.convo;

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
        PluginCommand command = plugin.getCommand("npc_convo_edit");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_convo_edit command");
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
        player.sendMessage(net.md_5.bungee.api.ChatColor.BLUE + "What would you like to edit about conversations?");

        TextComponent global = new TextComponent();
        global.setText("(Global Categories)");
        global.setUnderlined(true);
        global.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        global.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_convo_edit_global"));

        TextComponent local = new TextComponent();
        local.setText("(Local Categories)");
        local.setUnderlined(true);
        local.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        local.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_convo_edit_local"));

        TextComponent convo = new TextComponent();
        convo.setText("(Conversations)");
        convo.setUnderlined(true);
        convo.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        convo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_convo_edit_convo"));

        TextComponent response = new TextComponent();
        response.setText("(Responses)");
        response.setUnderlined(true);
        response.setColor(ChatColor.GREEN);
        response.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_convo_edit_response"));

        TextComponent separator = new TextComponent();
        separator.setText("   ");

        player.spigot().sendMessage(global, separator, local, separator, convo, separator, response);
        player.sendMessage("");
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<String> tabComplete = new ArrayList<>();
        return null;
    }
}

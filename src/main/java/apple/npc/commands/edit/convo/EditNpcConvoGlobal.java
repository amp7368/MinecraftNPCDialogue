package apple.npc.commands.edit.convo;

import apple.npc.ColorScheme;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.reading.command.convo.ReadingConvoGlobal;
import apple.npc.reading.command.npc.edit.ReadingNpcConclusionNum;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EditNpcConvoGlobal implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoGlobal(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("npc_convo_edit_global");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_convo_edit_global command");
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
        welcome.setText("Type what conversation global category would you like to edit?   ");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(ColorScheme.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s", CommandReferences.NPC_CONVO_EDIT)));
        player.spigot().sendMessage(welcome, back);

        StopCommand.startListening(new ReadingConvoGlobal(plugin), player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

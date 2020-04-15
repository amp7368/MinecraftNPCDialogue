package apple.npc.commands.edit.convo;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationGlobalCategory;
import apple.npc.data.convo.ConversationLocalCategory;
import apple.npc.reading.command.convo.ReadingConvoGlobal;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.Collection;
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
        player.sendMessage(MessageUtils.LONG_DASH);

        TextComponent welcome = new TextComponent();
        welcome.setText("Type what conversation global category would you like to edit?");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);

        Collection<String> globalNames = AllConversations.getList();
        for (String globalName : globalNames) {
            player.sendMessage(MessageUtils.DASH);

            TextComponent category = new TextComponent();
            category.setText(String.format("(Edit %s)", globalName));
            category.setUnderlined(true);
            category.setColor(MessageUtils.EDITING_OPTION);
            category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s",
                    CommandReferences.NPC_CONVO_EDIT_LOCAL, globalName)));
            player.spigot().sendMessage(category);
        }
        player.sendMessage(MessageUtils.DASH);
        player.sendMessage("");


        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(MessageUtils.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s", CommandReferences.NPC_CONVO_EDIT)));
        player.spigot().sendMessage(back);

        StopCommand.startListening(new ReadingConvoGlobal(plugin), player);

        player.sendMessage(MessageUtils.DASH);
        player.sendMessage(MessageUtils.LONG_DASH);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

package apple.npc.commands.edit.convo;

import apple.npc.ActionBar;
import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationLocalCategory;
import apple.npc.reading.command.convo.ReadingConvoLocal;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

public class EditNpcConvoLocal implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoLocal(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_LOCAL);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_LOCAL));
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
        if (args.length != 1) {
            commandSender.sendMessage(String.format(ChatColor.RED + "args length of %d is not valid.", args.length));
            return false;
        }


        String global = args[0];
        if (!AllConversations.hasGlobalCategory(global)) {
            player.sendMessage(ChatColor.RED + String.format("There is no %s conversation global category", global));
            return false;
        }

        TextComponent path = new TextComponent();
        path.setText(String.format("Convo | Global-(Local) | %s",global));
        path.setBold(MessageUtils.PATH_BOLD);
        path.setColor(MessageUtils.PATH);
        ActionBar.sendLongActionBar(player, path);

        player.sendMessage(MessageUtils.LONG_DASH);
        TextComponent welcome = new TextComponent();
        welcome.setText("What conversation local category would you like to edit?");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        player.spigot().sendMessage(welcome);

        Collection<ConversationLocalCategory> localCats = AllConversations.getLocalList(global);
        if (localCats == null) {
            player.sendMessage(ChatColor.RED + String.format("There is no %s conversation global category", global));
            return false;
        }

        player.sendMessage(MessageUtils.LONG_DASH);

        for (ConversationLocalCategory local : localCats) {
            player.sendMessage(MessageUtils.DASH);

            TextComponent category = new TextComponent();
            category.setText(String.format("(Edit %s:%s (UID:%d)", global, local.getName(), local.getUid()));
            category.setUnderlined(true);
            category.setColor(MessageUtils.EDITING_OPTION);
            category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %d",
                    CommandReferences.NPC_CONVO_EDIT_CONVO, global, local.getUid())));
            player.spigot().sendMessage(category);
        }
        player.sendMessage(MessageUtils.DASH);

        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(MessageUtils.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s", CommandReferences.NPC_CONVO_EDIT_GLOBAL)));
        player.sendMessage("");
        player.spigot().sendMessage(back);

        StopCommand.startListening(new ReadingConvoLocal(args[0]), player);

        player.sendMessage(MessageUtils.LONG_DASH);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
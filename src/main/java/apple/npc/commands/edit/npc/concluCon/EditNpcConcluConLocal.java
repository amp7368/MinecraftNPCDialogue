package apple.npc.commands.edit.npc.concluCon;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationLocalCategory;
import apple.npc.reading.command.npc.edit.conclusion.ReadingNpcConclusionGlobal;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.List;

public class EditNpcConcluConLocal implements CommandExecutor, TabCompleter {
    private JavaPlugin plugin;

    public EditNpcConcluConLocal(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_CONCLU_CON_LOCAL);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_CONCLU_CON_LOCAL));
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (args.length != 3) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        int uid;
        int concluNum;
        String global = args[2];
        try {
            uid = Integer.parseInt(args[0]);
            concluNum = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The first and second arguments must be a number");
            return false;
        }
        player.sendMessage(MessageUtils.LONG_DASH);

        TextComponent welcome = new TextComponent();
        welcome.setText("What conversation local category would you like it to refer to?");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        player.spigot().sendMessage(welcome);

        Collection<ConversationLocalCategory> localList = AllConversations.getLocalList(global);
        for (ConversationLocalCategory local : localList) {
            player.sendMessage(MessageUtils.DASH);

            TextComponent category = new TextComponent();
            category.setText(String.format("(%s)", local.getName()));
            category.setUnderlined(true);
            category.setColor(MessageUtils.EDITING_OPTION);
            category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d %s %d",
                    CommandReferences.NPC_EDIT_CONCLU_CONVO, uid, concluNum, global, local.getUid())));
            player.spigot().sendMessage(category);
        }
        player.sendMessage(MessageUtils.DASH);
        player.sendMessage("");


        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(MessageUtils.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d", CommandReferences.NPC_EDIT_CONCLU_CON_GLOBAL, uid, concluNum)));
        player.spigot().sendMessage(back);


        player.sendMessage(MessageUtils.DASH);
        player.sendMessage(MessageUtils.LONG_DASH);
        StopCommand.startListening(new ReadingNpcConclusionGlobal(uid, concluNum), player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

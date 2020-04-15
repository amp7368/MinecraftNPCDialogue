package apple.npc.commands.edit.npc.concluCon;

import apple.npc.ActionBar;
import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationLocalCategory;
import apple.npc.data.npc.NPCData;
import apple.npc.reading.command.npc.edit.conclusion.ReadingNpcConclusionGlobal;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class EditNpcConcluConCon implements CommandExecutor, TabCompleter {
    private JavaPlugin plugin;

    public EditNpcConcluConCon(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_CONCLU_CONVO);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_CONCLU_CONVO));
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
        if (args.length != 4) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        int uid;
        int concluNum;
        String global = args[2];
        int local;
        try {
            uid = Integer.parseInt(args[0]);
            concluNum = Integer.parseInt(args[1]);
            local = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The first, second, and fourth arguments must be a number");
            return false;
        }
        NPCData npc = AllNPCs.getNPCFromUID(uid);
        if (npc == null) {
            //todo enter a new npc
            return false;
        }
        String npcName = npc.name;
        String localName = AllConversations.getLocalName(global, local);
        if (localName == null) {
            //todo enter a new local category
            return false;
        }

        TextComponent path = new TextComponent();
        path.setText(String.format("Npc-Conclusion-Global-Local-(Conversation) | %s-%d-%s-%s", npcName, concluNum, global, localName));
        path.setBold(MessageUtils.PATH_BOLD);
        path.setColor(MessageUtils.PATH);
        ActionBar.sendLongActionBar(player, path);

        player.sendMessage(MessageUtils.LONG_DASH);

        TextComponent welcome = new TextComponent();
        welcome.setText("What conversation would you like it to refer to?");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        player.spigot().sendMessage(welcome);
        player.sendMessage(MessageUtils.DASH);

        ConversationLocalCategory localList = AllConversations.getLocalCategory(global, local);
        Map<Integer, ConversationData> conversations = localList.getConversations();
        for (ConversationData convo : conversations.values()) {

            TextComponent category = new TextComponent();
            category.setText(String.format("(%s)", convo.name));
            category.setUnderlined(true);
            category.setColor(MessageUtils.EDITING_OPTION);
            category.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d %s %d %d",
                    CommandReferences.NPC_EDIT_CONCLU_CON_MAKE, uid, concluNum, global, local, convo.uid)));
            player.spigot().sendMessage(category);
        }
        player.sendMessage(MessageUtils.DASH);
        player.sendMessage("");


        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(MessageUtils.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d %s", CommandReferences.NPC_EDIT_CONCLU_CON_GLOBAL, uid, concluNum, global)));
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
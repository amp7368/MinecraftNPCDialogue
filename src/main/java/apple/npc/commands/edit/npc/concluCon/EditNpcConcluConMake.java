package apple.npc.commands.edit.npc.concluCon;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConvoID;
import apple.npc.data.npc.NPCData;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class EditNpcConcluConMake implements CommandExecutor, TabCompleter {
    private JavaPlugin plugin;

    public EditNpcConcluConMake(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_CONCLU_CON_MAKE);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_CONCLU_CON_MAKE));
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
        if (args.length != 5) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        int uid;
        int concluNum;
        String global = args[2];
        int local;
        int convo;
        try {
            uid = Integer.parseInt(args[0]);
            concluNum = Integer.parseInt(args[1]);
            local = Integer.parseInt(args[3]);
            convo = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The first, second, and fourth arguments must be a number");
            return false;
        }
        ConversationData convoObject = AllConversations.get(new ConvoID(global, local, convo));
        if (convoObject == null) {
            player.sendMessage(MessageUtils.BAD + String.format("The convo %s:%d:%d does not exist", global, local, convo));
            return false;
        }

        NPCData npc = AllNPCs.getNPCFromUID(uid);
        if (npc == null) {
            player.sendMessage(MessageUtils.BAD + String.format("The npc %d does not exist does not exist", uid));
            return false;
        }
        Map<Integer, ConvoID> concluToConvo = npc.getConclusionsToConvo();
        concluToConvo.put(concluNum, new ConvoID(global, local, convo));

        AllNPCs.writeNpc(uid);

        String localName = AllConversations.getLocalName(global, local);
        String convoName = convoObject.name;

        player.sendMessage(MessageUtils.GOOD + String.format("The conclusion to convo %d -> (%s:%s:%s) has been created", concluNum, global, localName, convoName));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
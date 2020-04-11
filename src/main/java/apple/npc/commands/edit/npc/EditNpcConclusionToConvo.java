package apple.npc.commands.edit.npc;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.EditRedirect;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllNPCs;
import apple.npc.reading.command.npc.edit.ReadingNpcConclusionNum;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class that deals with the editing of conclusionsToConvo in the  npc session
 */
public class EditNpcConclusionToConvo implements CommandExecutor, TabCompleter {
    public EditNpcConclusionToConvo(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand("npc_edit_conclusionsToConvo");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the npc_edit_conclusionsToConvo command");
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

        if (args.length != 1) {
            commandSender.sendMessage(String.format(ChatColor.RED + "args length of %d is not valid.", args.length));
            return false;
        }
        int npcUID;
        try {
            npcUID = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(MessageUtils.BAD + "The first argument must be a number.");
            return false;
        }
        if (AllNPCs.hasUID(npcUID)) {
            player.sendMessage(ChatColor.BLUE + "What conclusion would you like to set?");
            Collection<Integer> conclusionList =  AllNPCs.getConclusionList(npcUID);
            if (conclusionList == null){
                // wtf happened we just checked that AllNPCs has a list
                player.sendMessage(MessageUtils.BAD + "There is no npc with uid " + npcUID);
                return false;
            }
            for (int conclusion :conclusionList) {
                player.sendMessage(MessageUtils.DASH);
                TextComponent conclu = new TextComponent();
                conclu.setText(String.format("(Edit Conclusion %d)", conclusion));
                conclu.setUnderlined(true);
                conclu.setColor(MessageUtils.EDITING_OPTION);
                conclu.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d",
                        CommandReferences.NPC_EDIT_CONCLU_CON_GLOBAL, npcUID, conclusion)));
                player.spigot().sendMessage(conclu);
            }
            StopCommand.startListening(new ReadingNpcConclusionNum(npcUID), player);
            player.sendMessage(MessageUtils.DASH);

            TextComponent back = new TextComponent();
            back.setText("(Back)");
            back.setUnderlined(true);
            back.setColor(MessageUtils.EDITING_OPTION);
            back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d",
                    CommandReferences.NPC_CONVO_EDIT_CONVO, npcUID)));
            player.sendMessage("");

            player.sendMessage(MessageUtils.DASH);
            player.sendMessage(MessageUtils.LONG_DASH);
        } else {
            player.sendMessage(MessageUtils.LONG_DASH);
            player.sendMessage(MessageUtils.BAD + "There is no npc with uid " + npcUID);
            player.sendMessage(MessageUtils.LONG_DASH);
            return false;
        }


        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}

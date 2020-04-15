package apple.npc.commands.edit.npc;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.npc.NPCData;
import apple.npc.reading.command.npc.edit.ReadingNpc;
import net.md_5.bungee.api.ChatMessageType;
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
 * a class that deals with the npc session for editing npcs
 */
public class EditNpc implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpc(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT));
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

        TextComponent path = new TextComponent();
        path.setText("Npc");
        path.setBold(MessageUtils.PATH_BOLD);
        path.setColor(MessageUtils.PATH);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,path);

        player.sendMessage(MessageUtils.LONG_DASH);

        player.sendMessage(ChatColor.BLUE + "Which npc do you want to edit?");


        TextComponent welcome = new TextComponent();
        welcome.setText("Which npc do you want to edit?");
        welcome.setColor(net.md_5.bungee.api.ChatColor.BLUE);

        Collection<NPCData> npcs = AllNPCs.getList();
        for (NPCData npc : npcs) {
            player.sendMessage(MessageUtils.DASH);

            TextComponent npcMessage = new TextComponent();
            npcMessage.setText(String.format("(Edit %s)", npc.name));
            npcMessage.setUnderlined(true);
            npcMessage.setColor(MessageUtils.EDITING_OPTION);
            npcMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d",
                    CommandReferences.NPC_NPC_EDIT, npc.uid)));
            player.spigot().sendMessage(npcMessage);
        }
        player.sendMessage(MessageUtils.DASH);
        player.sendMessage("");


        TextComponent back = new TextComponent();
        back.setText("(Back)");
        back.setUnderlined(true);
        back.setColor(MessageUtils.EDITING_OPTION);
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s", CommandReferences.NPC)));
        player.spigot().sendMessage(back);

        StopCommand.startListening(new ReadingNpc(plugin), player);

        player.sendMessage(MessageUtils.DASH);
        player.sendMessage(MessageUtils.LONG_DASH);
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}

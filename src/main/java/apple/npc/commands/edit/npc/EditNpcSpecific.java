package apple.npc.commands.edit.npc;

import apple.npc.ActionBar;
import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.npc.NPCData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EditNpcSpecific implements CommandExecutor, TabCompleter {
    public EditNpcSpecific(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_NPC_EDIT);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_NPC_EDIT));
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
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        int uid;
        try {
            uid = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The first argument must be a number");
            return false;
        }
        NPCData npc = AllNPCs.getNPCFromUID(uid);
        if (npc == null) {
            //todo enter a new npc
            return false;
        }
        String npcName = npc.name;

        TextComponent path = new TextComponent();
        path.setText(String.format("Npc | %s", npcName));
        path.setBold(MessageUtils.PATH_BOLD);
        path.setColor(MessageUtils.PATH);
        ActionBar.sendLongActionBar(player, path);

        player.sendMessage(ChatColor.BLUE + String.format("What would you like to edit about %s (uid=%d)?", npcName, uid));
        TextComponent editName = new TextComponent();
        editName.setText("(Name)");
        editName.setUnderlined(true);
        editName.setColor(ChatColor.GREEN);
        editName.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_NAME, uid)));

        TextComponent startingConclusion = new TextComponent();
        startingConclusion.setText("(Starting Conclusion)");
        startingConclusion.setUnderlined(true);
        startingConclusion.setColor(ChatColor.GREEN);
        startingConclusion.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit_startingconclusion " + uid));

        TextComponent startLocation = new TextComponent();
        startLocation.setText("(start location)");
        startLocation.setUnderlined(true);
        startLocation.setColor(ChatColor.GREEN);
        startLocation.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit_startLocation " + uid));

        TextComponent startPosition = new TextComponent();
        startPosition.setText("(start position)");
        startPosition.setUnderlined(true);
        startPosition.setColor(ChatColor.GREEN);
        startPosition.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit_startPosition " + uid));

        TextComponent varsToConclusions = new TextComponent();
        varsToConclusions.setText("(Vars to Conclusion)");
        varsToConclusions.setUnderlined(true);
        varsToConclusions.setColor(ChatColor.GREEN);
        varsToConclusions.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d ", CommandReferences.NPC_EDIT_VARS, uid)));

        TextComponent conclusionsToConvoUid = new TextComponent();
        conclusionsToConvoUid.setText("(Insert Convo)");
        conclusionsToConvoUid.setUnderlined(true);
        conclusionsToConvoUid.setColor(ChatColor.GREEN);
        conclusionsToConvoUid.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_CONCLU, uid)));

        player.sendMessage(MessageUtils.LONG_DASH);
        player.spigot().sendMessage(conclusionsToConvoUid);
        player.spigot().sendMessage(varsToConclusions);
        player.spigot().sendMessage(startLocation);
        player.spigot().sendMessage(startPosition);
        player.spigot().sendMessage(editName);
        player.spigot().sendMessage(startingConclusion);
        player.sendMessage(MessageUtils.LONG_DASH);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
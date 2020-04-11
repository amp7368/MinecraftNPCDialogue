package apple.npc.reading.command.npc.edit;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllNPCs;
import apple.npc.reading.command.ReadingCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ReadingNpc extends ReadingCommand {
    private JavaPlugin plugin;

    public ReadingNpc(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void dealWithStop(Player player) {
        String command = super.command;
        List<Integer> uids = AllNPCs.getNpcUids(command);
        if (uids.isEmpty()) {

            TextComponent yes = new TextComponent();
            yes.setText("(Yes)");
            yes.setUnderlined(true);
            yes.setColor(MessageUtils.EDITING_OPTION);
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s", CommandReferences.NPC_MAKE, command)));


            TextComponent no = new TextComponent();
            no.setText("(No)");
            no.setUnderlined(true);
            no.setColor(MessageUtils.EDITING_OPTION);
            no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s", CommandReferences.NPC_EDIT)));

            TextComponent separator = new TextComponent();
            separator.setText("   ");

            player.sendMessage(MessageUtils.LONG_DASH);
            player.sendMessage(MessageUtils.EDITING + String.format("The npc %s doesn't exist. Would you like to create it?", command));
            player.sendMessage("");
            player.spigot().sendMessage(yes, separator, no);
            player.sendMessage(MessageUtils.LONG_DASH);
            return;
        }
        if (uids.size() != 1) {
            player.sendMessage(ChatColor.BLUE + String.format("There are %d npc(s) named %s", uids.size(), command));
            return;
        }
        int uid = uids.get(0);
        Bukkit.getScheduler().callSyncMethod(plugin, () -> Bukkit.dispatchCommand(player, CommandReferences.NPC_NPC_EDIT + " " + uid));

    }
}

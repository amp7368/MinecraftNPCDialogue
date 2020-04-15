package apple.npc.reading.command.convo;

import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllConversations;
import apple.npc.reading.command.ReadingCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReadingConvoGlobal extends ReadingCommand {
    private JavaPlugin plugin;

    public ReadingConvoGlobal(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void dealWithStop(Player player) {
        String global = command;
        if (AllConversations.hasGlobalCategory(global)) {
            Bukkit.getScheduler().callSyncMethod(plugin, () -> Bukkit.dispatchCommand(player, CommandReferences.NPC_CONVO_EDIT_LOCAL + " " + global));
        } else {
            TextComponent yes = new TextComponent();
            yes.setText("(Yes)");
            yes.setUnderlined(true);
            yes.setColor(net.md_5.bungee.api.ChatColor.GREEN);
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_convo_make_global " + global));


            TextComponent no = new TextComponent();
            no.setText("(No)");
            no.setUnderlined(true);
            no.setColor(net.md_5.bungee.api.ChatColor.GREEN);
            no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_convo_edit_global"));

            TextComponent separator = new TextComponent();
            separator.setText("   ");
            player.sendMessage(ChatColor.BLUE + String.format("The global category '%s' doesn't exist. Would you like to create it?", global));
            player.spigot().sendMessage(yes, separator, no);
            player.sendMessage("");
        }
    }
}

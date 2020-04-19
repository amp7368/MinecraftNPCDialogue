package apple.npc.commands.edit.boolean_algebra.reading;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarCompDataStore;
import apple.npc.commands.edit.boolean_algebra.data.VarComparisonObject;
import apple.npc.data.all.AllVariables;
import apple.npc.reading.command.ReadingCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ReadBooleanCompVarName extends ReadingCommand {
    private JavaPlugin plugin;

    public ReadBooleanCompVarName(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void dealWithStop(Player player) {
        VarComparisonObject data = BooleanVarCompDataStore.get(player.getUniqueId());
        data.addComparisonLocal(command);

        List<Integer> uids = AllVariables.getVarLocalUIDs(data.global, command);

        if (uids.isEmpty()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.performCommand(String.format("%s %d",
                    CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_LOCAL, AllVariables.getNextUID(data.global))), 0);
        } else {
            player.sendMessage(MessageUtils.LONG_DASH);

            for (Integer uid : uids) {
                TextComponent uidText = new TextComponent();
                uidText.setText(String.format("%s-%d", command, uid));
                uidText.setColor(MessageUtils.EDITING_OPTION);
                uidText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d",
                        CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_LOCAL, uid)));
                player.spigot().sendMessage(uidText);
            }
            int uid = AllVariables.getNextUID(data.global);
            TextComponent uidText = new TextComponent();
            uidText.setText(String.format("Create %s-%d", command, uid));
            uidText.setColor(MessageUtils.EDITING_OPTION);
            uidText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d",
                    CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_LOCAL, uid)));
            player.spigot().sendMessage(uidText);
            player.sendMessage(MessageUtils.LONG_DASH);
        }


    }
}

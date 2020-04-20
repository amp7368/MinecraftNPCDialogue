package apple.npc.commands.edit.boolean_algebra.reading;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.commands.edit.boolean_algebra.data.BooleanDataStore;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarCompDataStore;
import apple.npc.data.all.AllVariables;
import apple.npc.data.booleanEditing.forced.BooleanEditForced;
import apple.npc.data.player.Variable;
import apple.npc.data.player.VariableCategory;
import apple.npc.reading.command.ReadingCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class ReadBooleanCompGlobal extends ReadingCommand {
    private JavaPlugin plugin;

    public ReadBooleanCompGlobal(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void dealWithStop(Player player) {
        BooleanVarCompDataStore.get(player.getUniqueId()).addComparisonGlobal(command);

        VariableCategory globalCategory = AllVariables.allVars.get(command);

        if (globalCategory == null) {
            player.sendMessage(MessageUtils.EDITING + "Scroll up. What is the global name of the player variable?");
            StopCommand.startListening(new ReadBooleanCompGlobal(plugin), player);
            return;
        }

        player.sendMessage(MessageUtils.LONG_DASH);
        player.sendMessage(MessageUtils.EDITING + "What is the local name of the player variable?");
        Map<Integer, Variable> variables = globalCategory.getVariables();
        if (variables.isEmpty()) {
            player.sendMessage(MessageUtils.EDITING + String.format("There are no variable names under '%s'", command));
        }
        for (Integer localUID : variables.keySet()) {
            Variable variable = variables.get(localUID);
            TextComponent localRead = new TextComponent();
            localRead.setText(String.format("(Local: %s)", variable.name));
            localRead.setUnderlined(true);
            localRead.setColor(MessageUtils.EDITING_OPTION);
            localRead.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s",
                    CommandReferences.FORCED_CHAT_SEND, variable.name)));
            player.spigot().sendMessage(localRead);
        }
        StopCommand.startListening(new ReadBooleanCompVarName(plugin), player);
        player.sendMessage(MessageUtils.LONG_DASH);
    }
}

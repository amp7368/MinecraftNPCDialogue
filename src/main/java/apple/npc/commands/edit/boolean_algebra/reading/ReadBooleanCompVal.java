package apple.npc.commands.edit.boolean_algebra.reading;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarCompDataStore;
import apple.npc.commands.edit.boolean_algebra.data.VarComparisonObject;
import apple.npc.data.all.AllVariables;
import apple.npc.reading.command.ReadingCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReadBooleanCompVal extends ReadingCommand {
    private JavaPlugin plugin;

    public ReadBooleanCompVal(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void dealWithStop(Player player) {
        int val;
        try {
            val = Integer.parseInt(command);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "This argument must be a number");
            StopCommand.startListening(new ReadBooleanCompVal(plugin), player);
            return;
        }
        player.sendMessage(MessageUtils.LONG_DASH);
        VarComparisonObject object = BooleanVarCompDataStore.get(player.getUniqueId());
        object.addComparisonVal(val);
        player.sendMessage(MessageUtils.EDITING + "What is the global category of the player variable?");

        for (String global : AllVariables.allVars.keySet()) {
            TextComponent globalRead = new TextComponent();
            globalRead.setText(String.format("(Global: %s)", global));
            globalRead.setUnderlined(true);
            globalRead.setColor(MessageUtils.EDITING_OPTION);
            globalRead.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s",
                    CommandReferences.FORCED_CHAT_SEND, global)));
            player.spigot().sendMessage(globalRead);
        }

        StopCommand.startListening(new ReadBooleanCompGlobal(plugin), player);
        player.sendMessage(MessageUtils.LONG_DASH);
    }
}

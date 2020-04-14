package apple.npc.commands.edit.boolean_algebra.reading.comp;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarConcluCompDataStore;
import apple.npc.commands.edit.boolean_algebra.data.VarConcluComparisonObject;
import apple.npc.data.all.AllPlayers;
import apple.npc.reading.command.ReadingCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class ReadBooleanCompVarName extends ReadingCommand {
    @Override
    public void dealWithStop(Player player) {
        player.sendMessage("dealing with stop for readBooleanCompVarName");
        VarConcluComparisonObject data = BooleanVarConcluCompDataStore.get(player.getUniqueId());
        data.addComparisonLocal(command);

        List<Integer> uids = AllPlayers.getVarLocalUIDs(data.global, command);

        if (uids.isEmpty()) {
            data.addComparisonLocalUID(AllPlayers.getNextUID(data.global));
        } else {
            player.sendMessage("finish me deal with non empty uid ");
            return;
        }
        player.sendMessage("What is the comparison type? (-2:< | -1:<= | 0:== | 1:>= | 2:>)");

        TextComponent lt = new TextComponent();
        lt.setText(String.format("%d < %s-%s", data.comparisonVal, data.global, data.local));
        lt.setColor(MessageUtils.EDITING_OPTION);
        lt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, -2)));
        player.spigot().sendMessage(lt);

        TextComponent lte = new TextComponent();
        lte.setText(String.format("%d <= %s-%s", data.comparisonVal, data.global, data.local));
        lte.setColor(MessageUtils.EDITING_OPTION);
        lte.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, -1)));
        player.spigot().sendMessage(lte);

        TextComponent ee = new TextComponent();
        ee.setText(String.format("%d == %s-%s", data.comparisonVal, data.global, data.local));
        ee.setColor(MessageUtils.EDITING_OPTION);
        ee.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, 0)));
        player.spigot().sendMessage(ee);

        TextComponent gte = new TextComponent();
        gte.setText(String.format("%d >= %s-%s", data.comparisonVal, data.global, data.local));
        gte.setColor(MessageUtils.EDITING_OPTION);
        gte.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, 1)));
        player.spigot().sendMessage(gte);

        TextComponent gt = new TextComponent();
        gt.setText(String.format("%d > %s-%s", data.comparisonVal, data.global, data.local));
        gt.setColor(MessageUtils.EDITING_OPTION);
        gt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d", CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP_TYPE, 2)));
        player.spigot().sendMessage(gt);


    }
}

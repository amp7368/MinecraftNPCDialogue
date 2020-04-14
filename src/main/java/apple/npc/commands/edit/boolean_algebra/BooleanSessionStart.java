package apple.npc.commands.edit.boolean_algebra;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.edit.boolean_algebra.data.BooleanDataStore;
import apple.npc.commands.edit.boolean_algebra.data.BooleanVarConcluCompDataStore;
import apple.npc.commands.edit.boolean_algebra.data.VarConcluComparisonObject;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.booleanEditing.forced.BooleanEditForced;
import apple.npc.data.booleanEditing.forced.BooleanEditForcedEmpty;
import apple.npc.data.booleanEditing.forced.BooleanEditForcedRedirect;
import apple.npc.data.npc.VarsConclusionMap;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BooleanSessionStart {

    public static void start(int npcUID, VarsConclusionMap varConclusion, Player player) {
        BooleanEditForced exp = BooleanEditForcedRedirect.make(varConclusion.getExpression(), 0, null);
        BooleanDataStore.put(player.getUniqueId(), exp);
        BooleanDataStore.put(player.getUniqueId(), npcUID, varConclusion.conclusionResult);
        BooleanVarConcluCompDataStore.put(player.getUniqueId());

        step(player);

    }

    public static void start(int npcUID, int concluNum, Player player) {
        BooleanDataStore.put(player.getUniqueId(), new BooleanEditForcedEmpty(0, null));
        BooleanDataStore.put(player.getUniqueId(), npcUID, concluNum);
        step(player);
    }

    public static void step(Player player) {
        UUID uid = player.getUniqueId();
        BooleanEditForced exp = BooleanDataStore.get(uid);

        printExp(player, exp.toString());

        if (exp.isFinished()) {
            Evaluateable finished = exp.toFinished();
            AllNPCs.setVarToConclu(BooleanDataStore.getNpcUid(uid), BooleanDataStore.getConclu(uid), finished);

            player.sendMessage("is done");
            return;
        }

        BooleanEditForced leftMost = exp.getLeftMost();
        player.sendMessage(String.format("What is exp%d going to be?", leftMost.getName()));

        TextComponent var = new TextComponent();
        var.setText("(Variable comparison)");
        var.setUnderlined(true);
        var.setColor(MessageUtils.EDITING_OPTION);
        var.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s",
                CommandReferences.NPC_EDIT_VARS_SPECIFIC_COMP)));
        player.spigot().sendMessage(var);

        TextComponent doubleExp = new TextComponent();
        doubleExp.setText("(Double expression)");
        doubleExp.setUnderlined(true);
        doubleExp.setColor(MessageUtils.EDITING_OPTION);
        doubleExp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s",
                CommandReferences.NPC_EDIT_VARS_SPECIFIC_DOUBLE)));
        player.spigot().sendMessage(doubleExp);
    }

    public static void printExp(Player player, String message) {
        player.sendMessage(message);
    }
}

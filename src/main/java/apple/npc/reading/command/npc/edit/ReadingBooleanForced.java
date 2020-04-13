package apple.npc.reading.command.npc.edit;

import apple.npc.afer_boolean.AfterVar;
import apple.npc.commands.edit.npc.AfterVarConclu;
import apple.npc.data.booleanEditing.forced.BooleanEditForced;
import apple.npc.data.booleanEditing.forced.BooleanEditForcedExpBase;
import apple.npc.reading.Reading;
import apple.npc.reading.command.ReadingCommand;
import apple.npc.reading.command.ResponseType;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class ReadingBooleanForced extends ReadingCommand {
    private BooleanEditForced bool;
    private int currentEdit;
    private AfterVar after;
    String patternDoubleExp = "exp[0-9]+";
    String patternDoubleOrOp = "||";
    String patternDoubleAndOp = "&&";

    String patternVarExp = "[0-9]+";
    String patternVarOp = "<|<=|==|>=|>";


    public ReadingBooleanForced(BooleanEditForced bool, AfterVar after, Player player) {
        this.bool = bool;
        this.after = after;
        nextEdit(player);

    }

    private void nextEdit(Player player) {
        if (bool.isFinished()) {
            after.dealWithDone(bool.toFinished());
        } else {
            int name = bool.nextExp();
            player.sendMessage(String.format("What is exp%d", name));
        }
    }

    @Override
    public void dealWithStop(Player player) {
        String[] args = command.split(" ");
        if (args.length != 3) {
            player.sendMessage("finsihe me");
            return;
        }

    }
}

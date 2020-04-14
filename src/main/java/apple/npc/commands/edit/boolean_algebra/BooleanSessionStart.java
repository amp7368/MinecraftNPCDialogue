package apple.npc.commands.edit.boolean_algebra;

import apple.npc.MessageUtils;
import apple.npc.afer_boolean.AfterPreResponse;
import apple.npc.afer_boolean.AfterVar;
import apple.npc.afer_boolean.AfterVarConclu;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.edit.boolean_algebra.data.AfterDataStore;
import apple.npc.commands.edit.boolean_algebra.data.BooleanDataStore;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.booleanEditing.forced.BooleanEditForced;
import apple.npc.data.booleanEditing.forced.BooleanEditForcedEmpty;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BooleanSessionStart {


    public static void start(int npcUID, int concluNum, Player player) {
        BooleanDataStore.put(player.getUniqueId(), new BooleanEditForcedEmpty(0, null));
        AfterDataStore.put(player.getUniqueId(), new AfterVarConclu(npcUID, concluNum));
        step(player);
    }

    public static void start(String global, int localUID, int convoUID, int responseUID, Player player) {
        BooleanDataStore.put(player.getUniqueId(), new BooleanEditForcedEmpty(0, null));
        AfterDataStore.put(player.getUniqueId(), new AfterPreResponse(global, localUID, convoUID, responseUID));
        step(player);
    }

    public static void step(Player player) {
        UUID uid = player.getUniqueId();
        BooleanEditForced exp = BooleanDataStore.get(uid);
        player.sendMessage(MessageUtils.LONG_DASH);

        printExp(player, exp.toString());

        if (exp.isFinished()) {
            BooleanDataStore.remove(uid);
            Evaluateable finished = exp.toFinished();
            AfterVar after = AfterDataStore.remove(uid);
            after.dealWithDone(player, finished);
            player.sendMessage(MessageUtils.LONG_DASH);
            return;
        }

        BooleanEditForced leftMost = exp.getLeftMost();
        player.sendMessage(String.format(MessageUtils.EDITING + "What is exp%d going to be?", leftMost.getName()));

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

        player.sendMessage(MessageUtils.LONG_DASH);
    }

    public static void printExp(Player player, String message) {
        List<TextComponent> text = new ArrayList<>();
        String[] wordsOld = message.split(" ");
        int length1 = wordsOld.length - 1;
        for (int i = 0; i < length1; i++) {
            wordsOld[i] = wordsOld[i] + " ";
        }
        ArrayList<String> words = new ArrayList<>(Arrays.asList(wordsOld));
        int size = words.size();
        for (int i = 0; i < size; i++) {
            List<StringBuilder> wordDivided = new ArrayList<>();
            StringBuilder section = new StringBuilder();
            char[] wordChars = words.remove(i).toCharArray();
            size--;
            for (char c : wordChars) {
                if (c == '(' || c == ')') {
                    wordDivided.add(section);
                    section = new StringBuilder();
                    wordDivided.add(new StringBuilder(String.valueOf(c)));
                } else {
                    section.append(c);
                }
            }
            wordDivided.add(section);
            for (StringBuilder wordSection : wordDivided) {
                words.add(i, wordSection.toString());
                size++;
                i++;
            }

        }
        for (String word : words) {
            TextComponent textWord = new TextComponent();
            if (word.equals("(") || word.equals(")")) {
                textWord.setColor(ChatColor.DARK_PURPLE);
            } else if (word.startsWith("§")) {
                word = word.replace("§", "");
                textWord.setColor(ChatColor.RED);
            } else {
                textWord.setColor(ChatColor.LIGHT_PURPLE);
            }
            textWord.setText(word);
            text.add(textWord);
        }

        player.spigot().sendMessage(text.toArray(new TextComponent[0]));
    }

}

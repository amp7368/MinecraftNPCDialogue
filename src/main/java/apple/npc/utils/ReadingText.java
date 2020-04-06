package apple.npc.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReadingText {
    protected List<String> text = new ArrayList<>();


    public void addText(String message) {
        text.add(message);
    }

    public void dealWithStop(Player player) {
    }
}

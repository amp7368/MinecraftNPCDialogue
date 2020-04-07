package apple.npc.reading.text;

import apple.npc.reading.Reading;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class ReadingText extends Reading {
    protected List<String> text = new ArrayList<>();


    public void addText(String message) {
        text.add(message);
    }

}

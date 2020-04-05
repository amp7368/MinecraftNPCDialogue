package apple.npc.creation.convo.info;

import java.util.List;

public class ConvoDataInfo {
    public int uid;
    public String name;
    public List<String> text;

    public ConvoDataInfo(int uid, String name, List<String> text) {
        this.uid = uid;
        this.name = name;
        this.text = text;
    }
}

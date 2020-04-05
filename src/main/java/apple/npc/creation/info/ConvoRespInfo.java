package apple.npc.creation.info;

import java.util.List;

public class ConvoRespInfo {
    public int uid;
    public List<String> text;

    public ConvoRespInfo(int uid, List<String> text) {
        this.uid = uid;
        this.text = text;
    }
}

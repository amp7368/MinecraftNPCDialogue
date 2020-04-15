package apple.npc.creation.from_scratch.convo.info;

import java.util.List;

/**
 * A base default conversation info (used for the creation of a conversation)
 */
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

package apple.npc.creation.from_scratch.convo.info;

import java.util.List;

/**
 * a conversation response info for creation of a base response in the file
 */
public class ConvoRespInfo {
    public int uid;
    public List<String> text;

    public ConvoRespInfo(int uid, List<String> text) {
        this.uid = uid;
        this.text = text;
    }
}

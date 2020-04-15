package apple.npc.creation.from_scratch.npc.info;

import apple.npc.data.convo.ConvoID;

public class NpcPlayerDataInfo {
    public final String playerUID;
    public final ConvoID conversation;
    public final int currentOpinion;
    public final String opinionName;

    public NpcPlayerDataInfo(String playerUID, ConvoID conversation, int currentOpinion, String opinionName) {
        this.playerUID = playerUID;
        this.conversation = conversation;
        this.currentOpinion = currentOpinion;
        this.opinionName = opinionName;
    }
}

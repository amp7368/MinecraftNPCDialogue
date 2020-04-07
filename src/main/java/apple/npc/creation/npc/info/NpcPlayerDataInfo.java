package apple.npc.creation.npc.info;

import apple.npc.data.convo.NpcConvoID;

public class NpcPlayerDataInfo {
    public final String playerUID;
    public final NpcConvoID conversation;
    public final int currentOpinion;
    public final String opinionName;

    public NpcPlayerDataInfo(String playerUID, NpcConvoID conversation, int currentOpinion, String opinionName) {
        this.playerUID = playerUID;
        this.conversation = conversation;
        this.currentOpinion = currentOpinion;
        this.opinionName = opinionName;
    }
}

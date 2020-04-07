package apple.npc.creation.npc.info;

import apple.npc.data.convo.NpcConvoID;

public class ConcluToConvoInfo {
    public int key;
    public NpcConvoID convoId;
    public ConcluToConvoInfo(int key,String global,int local,int uid){
        this.key = key;
        this.convoId = new NpcConvoID(global,local,uid);
    }
}

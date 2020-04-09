package apple.npc.creation.npc.info;

import apple.npc.data.convo.ConvoID;

public class ConcluToConvoInfo {
    public int key;
    public ConvoID convoId;
    public ConcluToConvoInfo(int key,String global,int local,int uid){
        this.key = key;
        this.convoId = new ConvoID(global,local,uid);
    }
}

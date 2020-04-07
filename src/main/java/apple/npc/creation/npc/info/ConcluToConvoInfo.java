package apple.npc.creation.npc.info;

import apple.npc.data.convo.ConvoID;

public class ConcluToConvoInfo {
    public String key;
    public ConvoID convoId;
    public ConcluToConvoInfo(String key,String global,int local,int uid){
        this.key = key;
        this.convoId = new ConvoID(global,local,uid);
    }
}

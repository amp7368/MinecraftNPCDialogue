package apple.npc.utils;

import apple.npc.data.all.AllConversations;

public class ReadingTextConvo extends ReadingText {
    private String global;
    private int local;
    private String convo;


    public ReadingTextConvo(String global, int local, String convoUID) {
        this.global = global;
        this.local = local;
        this.convo = convoUID;
    }

    @Override
    public void dealWithStop() {
        if (AllConversations.createConvo(global, local, convo, super.text)) {

        } else {

        }
    }
}

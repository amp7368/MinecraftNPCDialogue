package apple.npc.utils;

public class ReadingTextResponse extends ReadingText {
    private String global;
    private int local;
    private int convo;


    public ReadingTextResponse(String global, int local, int convoUID) {
        this.global = global;
        this.local = local;
        this.convo = convoUID;
    }
    @Override
    public void dealWithStop(){

    }
}

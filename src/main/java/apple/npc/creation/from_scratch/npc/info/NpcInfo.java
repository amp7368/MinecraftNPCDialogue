package apple.npc.creation.from_scratch.npc.info;

public class NpcInfo {
    public String name;
    public int personalUID;
    public String gameUID;
    public int startingConclu;
    public long maxTimeSinceTalk;

    public NpcInfo(String name, int personalUID, String gameUID, int startingConclu, long maxTimeSinceTalk) {
        this.name = name;
        this.personalUID = personalUID;
        this.gameUID = gameUID;
        this.startingConclu = startingConclu;
        this.maxTimeSinceTalk = maxTimeSinceTalk;
    }
}

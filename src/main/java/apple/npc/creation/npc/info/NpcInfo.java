package apple.npc.creation.npc.info;

public class NpcInfo {
    public String name;
    public int personalUID;
    public String gameUID;
    public int startingConclu;

    public NpcInfo(String name, int personalUID, String gameUID, int startingConclu) {
        this.name = name;
        this.personalUID = personalUID;
        this.gameUID = gameUID;
        this.startingConclu = startingConclu;
    }
}

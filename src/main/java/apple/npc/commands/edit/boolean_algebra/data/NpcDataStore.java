package apple.npc.commands.edit.boolean_algebra.data;

import java.util.HashMap;
import java.util.UUID;

public class NpcDataStore {
    private static HashMap<UUID, Integer> npcUIDs = new HashMap<>();
    private static HashMap<UUID, Integer> conclusions = new HashMap<>();

    public static void put(UUID uniqueId, int npcUID, int conclusionResult) {
        npcUIDs.put(uniqueId, npcUID);
        conclusions.put(uniqueId, conclusionResult);
    }

    public static int getNpcUid(UUID uid) {
        return npcUIDs.getOrDefault(uid, null);
    }

    public static int getConclu(UUID uid) {
        return conclusions.getOrDefault(uid, null);
    }

    public static void remove(UUID player) {
        npcUIDs.remove(player);
        conclusions.remove(player);
    }

}

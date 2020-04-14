package apple.npc.commands.edit.boolean_algebra.data;

import apple.npc.data.booleanEditing.forced.BooleanEditForced;

import java.util.HashMap;
import java.util.UUID;

public class BooleanDataStore {
    private static HashMap<UUID, BooleanEditForced> store = new HashMap<>();
    private static HashMap<UUID, Integer> npcUIDs = new HashMap<>();
    private static HashMap<UUID, Integer> conclusions = new HashMap<>();

    public static void put(UUID player, BooleanEditForced exp) {
        store.put(player, exp);
    }

    public static BooleanEditForced get(UUID player) {
        return store.get(player);
    }

    public static void remove(UUID player) {
        store.remove(player);
        npcUIDs.remove(player);
        conclusions.remove(player);
    }

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
}

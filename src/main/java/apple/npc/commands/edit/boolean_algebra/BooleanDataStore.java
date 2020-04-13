package apple.npc.commands.edit.boolean_algebra;

import apple.npc.data.booleanEditing.forced.BooleanEditForced;

import java.util.HashMap;
import java.util.UUID;

public class BooleanDataStore {
    private static HashMap<UUID, BooleanEditForced> store = new HashMap<>();

    public static void put(UUID player, BooleanEditForced exp) {
        store.put(player, exp);
    }

    public static BooleanEditForced get(UUID player) {
        return store.get(player);
    }

    public static void remove(UUID player) {
        store.remove(player);
    }
}

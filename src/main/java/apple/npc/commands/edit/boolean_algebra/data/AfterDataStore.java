package apple.npc.commands.edit.boolean_algebra.data;

import apple.npc.afer_boolean.AfterVar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfterDataStore {
    private static Map<UUID, AfterVar> after = new HashMap<>();

    public static void put(UUID player, AfterVar afterVar) {
        after.put(player, afterVar);
    }

    public static AfterVar get(UUID player) {
        return after.get(player);
    }

    public static AfterVar remove(UUID player) {
        return after.remove(player);
    }

}

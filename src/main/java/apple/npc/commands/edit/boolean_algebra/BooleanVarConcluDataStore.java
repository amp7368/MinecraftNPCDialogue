package apple.npc.commands.edit.boolean_algebra;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BooleanVarConcluDataStore {
    private static Map<UUID, Integer> npc = new HashMap<>();
    private static Map<UUID, Integer> conclusion = new HashMap<>();

    public static void put(UUID uniqueId, int npcUID, int conclusionResult) {
        npc.put(uniqueId, npcUID);
        conclusion.put(uniqueId, conclusionResult);
    }
}

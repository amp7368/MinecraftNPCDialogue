package apple.npc.commands.edit.boolean_algebra.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BooleanVarConcluDoubDataStore {
    private static Map<UUID, VarConcluDoubleObject> varConclu = new HashMap<>();

    public static void put(UUID uniqueId, int npcUID, int conclusionResult) {
        varConclu.put(uniqueId, new VarConcluDoubleObject(npcUID, conclusionResult));
    }

    public static VarConcluDoubleObject get(UUID uniqueId) {
        return varConclu.get(uniqueId);
    }
}

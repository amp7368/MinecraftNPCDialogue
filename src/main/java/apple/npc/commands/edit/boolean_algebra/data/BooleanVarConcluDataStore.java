package apple.npc.commands.edit.boolean_algebra.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BooleanVarConcluDataStore {
    private static Map<UUID, VarConcluObject> varConclu = new HashMap<>();

    public static void put(UUID uniqueId, int npcUID, int conclusionResult) {
        varConclu.put(uniqueId, new VarConcluObject(npcUID,conclusionResult));
    }

    public static VarConcluObject get(UUID uniqueId) {
        return varConclu.get(uniqueId);
    }
}

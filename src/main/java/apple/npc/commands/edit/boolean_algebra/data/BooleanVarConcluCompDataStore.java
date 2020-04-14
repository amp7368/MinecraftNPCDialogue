package apple.npc.commands.edit.boolean_algebra.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BooleanVarConcluCompDataStore {
    private static Map<UUID, VarConcluComparisonObject> varConclu = new HashMap<>();

    public static void put(UUID uniqueId) {
        varConclu.put(uniqueId, new VarConcluComparisonObject());
    }

    public static VarConcluComparisonObject get(UUID uniqueId) {
        return varConclu.get(uniqueId);
    }
}

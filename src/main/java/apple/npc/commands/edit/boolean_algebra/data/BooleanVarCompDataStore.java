package apple.npc.commands.edit.boolean_algebra.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BooleanVarCompDataStore {
    private static Map<UUID, VarComparisonObject> varConclu = new HashMap<>();

    public static void put(UUID uniqueId) {
        varConclu.put(uniqueId, new VarComparisonObject());
    }

    public static VarComparisonObject get(UUID uniqueId) {
        return varConclu.get(uniqueId);
    }
}

package apple.npc.data.all;

import apple.npc.data.single.NPCData;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AllNPCs {

    private static Map<Integer, NPCData> allUIDToNpcs = new HashMap<>();
    private static Map<String, NPCData> allGameUIDToNpcs = new HashMap<>();

    public static void initialize(File dataFolder) {
        File directory = new File(String.format("%s%s%s", dataFolder, File.separator, "npcData"));
        String[] pathNameList = directory.list();
        if (pathNameList == null) {
            System.err.println(String.format("%s%s%s", "Could not get any files with path name of \"", String.format("%s%s%s", dataFolder, File.separator, "npcData"), "\""));
            return;
        }
        for (String pathName : pathNameList) {
            File file = new File(String.format("%s%s%s%s%s", dataFolder, File.separator, "npcData", File.separator, pathName));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            NPCData npc = new NPCData(config);
            allGameUIDToNpcs.put(npc.gameUID, npc);
            allUIDToNpcs.put(npc.uid, npc);
        }
    }

    public static NPCData getNPC(String uid) {
        return allGameUIDToNpcs.get(uid);
    }

    public static NPCData getNPC(int uid) {
        return allUIDToNpcs.get(uid);
    }
}

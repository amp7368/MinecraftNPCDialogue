package apple.npc.data.all;

import apple.npc.data.single.NPCData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllNPCs {

    public static Map<Integer, NPCData> allNpcs = new HashMap<>();

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
            allNpcs.put(npc.uid, npc);
        }
        for (Integer uid : allNpcs.keySet()) {
            System.out.println(uid);
            String[] strings = allNpcs.get(uid).toString().split("\n");
            for (String string : strings)
                System.out.println(string);
        }
        System.out.println("\n");
    }
}

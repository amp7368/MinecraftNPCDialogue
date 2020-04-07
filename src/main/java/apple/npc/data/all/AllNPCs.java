package apple.npc.data.all;

import apple.npc.creation.npc.info.NpcInfo;
import apple.npc.creation.npc.single.CreateNpcData;
import apple.npc.data.single.NPCData;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllNPCs {

    private static Map<Integer, NPCData> allUIDToNpcs = new HashMap<>();
    private static Map<String, NPCData> allGameUIDToNpcs = new HashMap<>();
    private static File folder;

    public static void initialize(File dataFolder) {
        folder = dataFolder;
        File directory = new File(String.format("%s%s%s", dataFolder, File.separator, YMLFileNavigate.NPC_FOLDER));
        String[] pathNameList = directory.list();
        if (pathNameList == null) {
            System.err.println(String.format("%s%s%s", "Could not get any files with path name of \"", String.format("%s%s%s", dataFolder, File.separator, YMLFileNavigate.NPC_FOLDER), "\""));
            return;
        }
        for (String pathName : pathNameList) {
            File file = new File(String.format("%s%s%s%s%s", dataFolder, File.separator, YMLFileNavigate.NPC_FOLDER, File.separator, pathName));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            NPCData npc = new NPCData(config);
            allGameUIDToNpcs.put(npc.gameUID, npc);
            allUIDToNpcs.put(npc.uid, npc);
        }
    }


    public static boolean makeNPC(String name, Location location) {
        int uid = 0;
        while (getNPCFromUID(uid) != null) {
            uid++;
        }
        World world = location.getWorld();
        if (world == null) {
            System.out.println("The player doesn't exist in a world?");
            return false;
        }
        Entity stand = world.spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setInvulnerable(true);
        stand.setCustomNameVisible(true);
        stand.setCustomName(name);
        String gameUID = stand.getUniqueId().toString();
        if (CreateNpcData.create(folder.getPath(), name, new NpcInfo(name, uid, gameUID, -1))) {
            File file = new File(String.format("%s%s%s%s%s%c%s%s", folder.getPath(), File.separator, YMLFileNavigate.NPC_FOLDER, File.separator, uid, ',', name, YMLFileNavigate.YML));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            NPCData npc = new NPCData(config);
            allUIDToNpcs.put(uid, npc);
            allGameUIDToNpcs.put(gameUID, npc);
        }
        return true;
    }

    public static NPCData getNPCFromUID(String uid) {
        if (!allGameUIDToNpcs.containsKey(uid))
            return null;
        return allGameUIDToNpcs.get(uid);
    }

    public static NPCData getNPCFromUID(int uid) {
        if (!allUIDToNpcs.containsKey(uid))
            return null;
        return allUIDToNpcs.get(uid);
    }

    public static List<Integer> getNpcUids(String npcName) {
        List<Integer> uids = new ArrayList<>();
        for (NPCData npc : allUIDToNpcs.values()) {
            if (npc.name.equals(npcName)) {
                uids.add(npc.uid);
            }
        }
        return uids;
    }
}

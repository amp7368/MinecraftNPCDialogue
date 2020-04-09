package apple.npc.data.all;

import apple.npc.creation.from_scratch.npc.components.CreateConcluToConvo;
import apple.npc.creation.from_scratch.npc.components.CreateNpcPlayerData;
import apple.npc.creation.from_scratch.npc.info.ConcluToConvoInfo;
import apple.npc.creation.from_scratch.npc.info.NpcInfo;
import apple.npc.creation.from_scratch.npc.info.NpcPlayerDataInfo;
import apple.npc.creation.from_scratch.npc.single.CreateNpcData;
import apple.npc.data.convo.ConvoID;
import apple.npc.data.npc.NPCData;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllNPCs {

    private static final long MINUTES_5 = 300000;
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
            readNpc(pathName);
        }
    }

    private static void readNpc(String fileName) {
        File file = new File(String.format("%s%s%s%s%s", folder.getPath(), File.separator, YMLFileNavigate.NPC_FOLDER, File.separator, fileName));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        NPCData npc = new NPCData(config);
        allGameUIDToNpcs.put(npc.gameUID, npc);
        allUIDToNpcs.put(npc.uid, npc);
    }


    public static boolean makeNPC(String name, Location location) {
        int uid = 0;
        while (getNPCFromUID(uid) != null) {
            uid++;
        }
        World world = location.getWorld();
        if (world == null) {
            System.err.println("The player doesn't exist in a world?");
            return false;
        }
        Entity stand = world.spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setInvulnerable(true);
        stand.setCustomNameVisible(true);
        stand.setCustomName(name);
        String gameUID = stand.getUniqueId().toString();
        if (CreateNpcData.create(folder.getPath(), name, new NpcInfo(name, uid, gameUID, -1, MINUTES_5))) {
            File file = new File(String.format("%s%s%s%s%s%c%s%s", folder.getPath(), File.separator, YMLFileNavigate.NPC_FOLDER, File.separator, uid, ',', name, YMLFileNavigate.YML));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            NPCData npc = new NPCData(config);
            allUIDToNpcs.put(uid, npc);
            allGameUIDToNpcs.put(gameUID, npc);
        }
        return true;
    }

    public static void setConcluToConvo(int npcUID, int concluNum, String global, int local, int convo) {
        if (allUIDToNpcs.containsKey(npcUID)) {
            CreateConcluToConvo.set(folder.getPath().toString(), String.valueOf(npcUID), allUIDToNpcs.get(npcUID).name,
                    new ConcluToConvoInfo(concluNum, global, local, convo));
            readNpc(npcUID + "," + allUIDToNpcs.get(npcUID).name + YMLFileNavigate.YML);
        }

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

    public static boolean hasUID(int npcUID) {
        return allUIDToNpcs.containsKey(npcUID);
    }

    public static boolean hasGameUID(String gameUID) {
        return allGameUIDToNpcs.containsKey(gameUID);
    }

    public static void setPlayerData(int npcUID, String npcName, String playerUID, ConvoID conversation, int currentOpinion, String opinionName) {
        CreateNpcPlayerData.set(String.format("%s%s%s", folder.getPath(), File.separator, YMLFileNavigate.NPC_FOLDER),
                String.valueOf(npcUID), npcName, new NpcPlayerDataInfo(playerUID, conversation, currentOpinion, opinionName));
        readNpc(npcUID + "," + allUIDToNpcs.get(npcUID).name + YMLFileNavigate.YML);
    }

    public static void respond(Player player, int npcUid, int responseUid) {
        if(allUIDToNpcs.containsKey(npcUid))
        allUIDToNpcs.get(npcUid).respond(player,responseUid);
        else
            player.sendMessage("Sorry to ruin immersion, but something went wrong getting the npc given your response.");
    }
}

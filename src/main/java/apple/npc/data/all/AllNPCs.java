package apple.npc.data.all;

import apple.npc.creation.from_data.npc.WriteNpcAll;
import apple.npc.creation.from_scratch.npc.info.NpcInfo;
import apple.npc.creation.from_scratch.npc.single.CreateNpcData;
import apple.npc.data.booleanAlgebra.Evaluateable;
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
import java.util.*;

public class AllNPCs {

    private static final long MINUTES_5 = 300000;
    private static Map<Integer, NPCData> allUIDToNpcs;
    private static Map<String, NPCData> allGameUIDToNpcs;
    private static File folder;

    public static void initialize(File dataFolder) {
        allUIDToNpcs = new HashMap<>();
        allGameUIDToNpcs = new HashMap<>();
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
        File file = new File(String.format("%s%s%s%s%s", folder.getPath(), File.separator, YMLFileNavigate.NPC_FOLDER,
                File.separator, fileName));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        NPCData npc = new NPCData(config);
        allGameUIDToNpcs.put(npc.gameUID, npc);
        allUIDToNpcs.put(npc.uid, npc);
    }

    public static void writeNpc(int npcUID) {
        WriteNpcAll.write(folder.getPath(), npcUID, allUIDToNpcs.get(npcUID).name);
        for (String gameUID : allGameUIDToNpcs.keySet()) {
            if (allGameUIDToNpcs.get(gameUID).uid == npcUID) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(String.format("%s%s%s%s%d%c%s%s",
                        folder.getPath(), File.separator, YMLFileNavigate.NPC_FOLDER, File.separator, npcUID, ',', allUIDToNpcs.get(npcUID).name, YMLFileNavigate.YML)));
                allGameUIDToNpcs.put(gameUID, new NPCData(config));
            }
        }
    }

    public static void writeAll() {
        for (int npcUID : allUIDToNpcs.keySet()) {
            writeNpc(npcUID);
        }
    }

    public static void readAll() {
        initialize(folder);
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
            allUIDToNpcs.get(npcUID).setConcluToConvo(concluNum, global, local, convo);
            writeNpc(npcUID);
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

    public static void setPlayerData(int npcUID, String playerUID, ConvoID conversation, int currentOpinion, String opinionName) {
        if (allUIDToNpcs.containsKey(npcUID)) {
            allUIDToNpcs.get(npcUID).setPlayerData(playerUID, conversation, currentOpinion, opinionName);
            writeNpc(npcUID);
        }
    }

    public static void respond(Player player, int npcUid, int responseUid) {
        if (allUIDToNpcs.containsKey(npcUid))
            allUIDToNpcs.get(npcUid).respond(player, responseUid);
        else
            player.sendMessage("Sorry to ruin immersion, but something went wrong getting the npc given your response.");
        writeNpc(npcUid);
    }

    public static void deleteFile(NPCData npcData) {
        File deleteMe = new File(String.format("%s%s%s%s%d%c%s%s",
                folder.getPath(), File.separator, YMLFileNavigate.NPC_FOLDER, File.separator, npcData.uid, ',',
                npcData.name, YMLFileNavigate.YML));
        deleteMe.delete();
    }

    public static Collection<NPCData> getList() {
        return allUIDToNpcs.values();
    }

    public static Collection<Integer> getConclusionList(int npcUID) {
        if (allUIDToNpcs.containsKey(npcUID)) {
            return allUIDToNpcs.get(npcUID).getConclusionList();
        } else {
            return null;
        }
    }

    public static void setVarToConclu(int npcUID, int conclusionResult, Evaluateable finished) {
        if (allUIDToNpcs.containsKey(npcUID)) {
            allUIDToNpcs.get(npcUID).setVarToConclu(conclusionResult, finished);
            writeNpc(npcUID);
        }
    }
}

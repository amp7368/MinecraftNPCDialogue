package apple.npc.data.all;

import apple.npc.creation.from_data.player.WritePlayerAll;
import apple.npc.data.convo.VariableChange;
import apple.npc.data.npc.NPCData;
import apple.npc.data.player.PlayerData;
import apple.npc.data.player.Variable;
import apple.npc.data.player.VariableCategory;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class AllPlayers {

    public static final int DEFAULT_PLAYER_VAR_VAL = 0;
    private static Map<String, PlayerData> allPlayers = new HashMap<>();
    private static File folder;

    public static void initialize(File dataFolder) {
        folder = dataFolder;
        File directory = new File(String.format("%s%s%s", dataFolder, File.separator, YMLFileNavigate.PLAYER_FOLDER));
        String[] pathNameList = directory.list();
        if (pathNameList == null) {
            System.err.println(String.format("%s%s%s", "Could not get any files with path name of \"", String.format("%s%s%s", dataFolder, File.separator, YMLFileNavigate.PLAYER_FOLDER), "\""));
            return;
        }
        for (String pathName : pathNameList) {
            File file = new File(String.format("%s%s%s%s%s", dataFolder, File.separator, YMLFileNavigate.PLAYER_FOLDER, File.separator, pathName));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            allPlayers.put(pathName.replace(".yml", ""), new PlayerData(config));
        }
    }

    public static void doVariableChange(NPCData npc, String playerUID, HashSet<VariableChange> variableChanges) {
        if (!allPlayers.containsKey(playerUID)) {
            allPlayers.put(playerUID, new PlayerData(playerUID));
        }

        allPlayers.get(playerUID).doVariableChanges(npc,variableChanges);
        writeplayer(playerUID);
    }

    public static int getVarVal(String playerUID, String comparisonVarGlobal, int comparisonVarUID) {
        if (allPlayers.containsKey(playerUID))
            return allPlayers.get(playerUID).getVarVal(comparisonVarGlobal, comparisonVarUID);
        else
            return DEFAULT_PLAYER_VAR_VAL;
    }


    public static PlayerData getPlayer(String uid) {
        return allPlayers.get(uid);
    }

    public static void readAll() {
        initialize(folder);
    }

    public static void writeAll() {
        for (String player : allPlayers.keySet()) {
            writeplayer(player);
        }
    }

    private static void writeplayer(String player) {
        //todo make it only read one player
        if (allPlayers.containsKey(player))
            WritePlayerAll.write(folder.getPath(), player);
    }

}

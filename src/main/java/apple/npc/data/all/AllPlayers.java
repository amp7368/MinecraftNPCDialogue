package apple.npc.data.all;

import apple.npc.creation.from_data.player.WritePlayerAll;
import apple.npc.data.convo.VariableChange;
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
    public static HashMap<String, VariableCategory> allVars = new HashMap<>();
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

    public static void doVariableChange(String playerUID, HashSet<VariableChange> variableChanges) {
        if (!allPlayers.containsKey(playerUID)) {
            allPlayers.put(playerUID,new PlayerData(playerUID));
        }
        allPlayers.get(playerUID).doVariableChanges(variableChanges);

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

    public static void addVar(String global, Variable local) {
        if (!allVars.containsKey(global)) {
            allVars.put(global, new VariableCategory());
        }
        allVars.get(global).addVar(local);
    }

    public static List<Integer> getVarLocalUIDs(String global, String localName) {
        if (allVars.containsKey(global)) {
            return allVars.get(global).getVarLocalUIDs(localName);
        }
        return new ArrayList<>();
    }

    public static int getNextUID(String global) {
        if (allVars.containsKey(global)) {
            return allVars.get(global).getNextUID();
        }
        return 0;
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

package apple.npc.data.all;

import apple.npc.data.single.PlayerData;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AllPlayers {

    private static Map<String, PlayerData> allPlayers = new HashMap<>();

    public static void initialize(File dataFolder) {
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
        for (PlayerData player : allPlayers.values()) {
            String[] strings = player.toString().split("\n");
            for (String string : strings)
                System.out.println(string);
        }
        System.out.println("\n");
    }

    public static int getVarVal(String playerUID, String comparisonVarGlobal, int comparisonVarUID) {
        return allPlayers.get(playerUID).getVarVal(comparisonVarGlobal, comparisonVarUID);
    }

    public static PlayerData getPlayer(String uid) {
        return allPlayers.get(uid);
    }
}

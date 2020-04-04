package apple.npc.data.all;

import apple.npc.data.single.PlayerData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllPlayers {

    public static Map<String, PlayerData> allPlayers = new HashMap<>();

    public static void initialize(File dataFolder) {
        File directory = new File(String.format("%s%s%s", dataFolder, File.separator, "playerData"));
        String[] pathNameList = directory.list();
        if (pathNameList == null) {
            System.err.println(String.format("%s%s%s", "Could not get any files with path name of \"", String.format("%s%s%s", dataFolder, File.separator, "playerData"), "\""));
            return;
        }
        for (String pathName : pathNameList) {
            File file = new File(String.format("%s%s%s%s%s", dataFolder, File.separator, "playerData", File.separator, pathName));
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
}

package apple.npc.data.all;

import apple.npc.data.single.PlayerData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class AllPlayers {
    private static JavaPlugin plugin;

    public static ArrayList<PlayerData> allPlayers = new ArrayList<>();

    public static void initialize(JavaPlugin plugin) {
        File directory = new File(String.format("%s%s%s", plugin.getDataFolder(), File.separator, "playerData"));
        String[] pathNameList = directory.list();
        if (pathNameList == null) {
            System.err.println(String.format("%s%s%s", "Could not get any files with path name of \"", String.format("%s%s%s", plugin.getDataFolder(), File.separator, "playerData"), "\""));
            return;
        }
        for (String pathName : pathNameList) {
            File file = new File(String.format("%s%s%s%s%s", plugin.getDataFolder(), File.separator, "playerData", File.separator, pathName));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            allPlayers.add(new PlayerData(config));
        }
        for (PlayerData player : allPlayers) {
            String[] strings = player.toString().split("\n");
            for (String string : strings)
                System.out.println(string);
        }
    }
}

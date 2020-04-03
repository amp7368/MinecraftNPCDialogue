package apple.npc.data.single;

import apple.npc.data.utils.VarsConclusionMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NPCData {
    public int uid;
    public String name;
    public String gameUID;
    public VarsConclusionMap varsToConclusion;
    public Map<String, Integer> conclusionsToConvo;
    public Map<String, NPCPlayerData> playerDataMap;

    public NPCData(YamlConfiguration config) {
        uid = config.getInt("uid");
        name = config.getString("name");
        gameUID = config.getString("gameUID");
        varsToConclusion = new VarsConclusionMap(config.getConfigurationSection("varsToConclusions"));
        conclusionsToConvo = mapConclusionsToConvo(config.getConfigurationSection("conclusionsToConvoUid"));

        ConfigurationSection playersUIDsConfig = config.getConfigurationSection("playersuid");
        Set<String> playersUIDs = playersUIDsConfig.getKeys(false);
        for (String uid : playersUIDs) {
            playerDataMap.put(uid, new NPCPlayerData(playersUIDsConfig.getConfigurationSection(uid)));
        }

    }

    private Map<String, Integer> mapConclusionsToConvo(ConfigurationSection config) {
        Map<String, Integer> map = new HashMap<>();
        Set<String> conclusions = config.getKeys(false);
        for (String conclusion : conclusions) {
            map.put(conclusion, config.getInt(conclusion));
        }
        return map;
    }
}
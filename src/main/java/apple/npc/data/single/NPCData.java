package apple.npc.data.single;

import apple.npc.data.components.VarsConclusionMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NPCData {
    public int uid;
    private String name;
    public String gameUID;
    private ArrayList<VarsConclusionMap> varsToConclusion;
    private Map<Integer, ConvoID> conclusionsToConvo;
    private Map<String, NPCPlayerData> playerDataMap;

    public NPCData(YamlConfiguration config) {
        playerDataMap = new HashMap<>();
        uid = config.getInt("uid");
        name = config.getString("name");
        gameUID = config.getString("gameUID");

        varsToConclusion = new ArrayList<>();
        ConfigurationSection varConcluConfig = config.getConfigurationSection("varsToConclusions");
        Set<String> varConcluKeys = varConcluConfig.getKeys(false);
        for (String varConcluKey : varConcluKeys) {
            varsToConclusion.add(new VarsConclusionMap(varConcluConfig.getConfigurationSection(varConcluKey)));
        }
        conclusionsToConvo = mapConclusionsToConvo(config.getConfigurationSection("conclusionsToConvoUid"));

        ConfigurationSection playersUIDsConfig = config.getConfigurationSection("playersuid");
        Set<String> playersUIDs = playersUIDsConfig.getKeys(false);
        for (String uid : playersUIDs) {
            playerDataMap.put(uid, new NPCPlayerData(playersUIDsConfig.getConfigurationSection(uid)));
        }
    }

    private Map<Integer, ConvoID> mapConclusionsToConvo(ConfigurationSection config) {
        Map<Integer, ConvoID> map = new HashMap<>();
        Set<String> conclusions = config.getKeys(false);
        for (String conclusion : conclusions) {
            map.put(config.getInt(conclusion),new ConvoID(config.getConfigurationSection(conclusion)));
        }
        return map;
    }

    public String toString() {
        return String.format("uid:%d, name:%s, gameUID:%s", uid, name, gameUID);
    }

    public void doConversation(PlayerData player) {

    }
}
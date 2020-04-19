package apple.npc.creation.from_data.npc;

import apple.npc.data.all.AllNPCs;
import apple.npc.data.convo.ConvoID;
import apple.npc.data.npc.NPCData;
import apple.npc.data.npc.NPCPlayerData;
import apple.npc.data.npc.VarsConclusionMap;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class WriteNpcAll {
    public static void write(String folder, int npcUID, String name) {
        File file = new File(String.format("%s%s%s%s%s%c%s%s", folder, File.separator, YMLFileNavigate.NPC_FOLDER,
                File.separator, npcUID, ',', name, YMLFileNavigate.YML));
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.err.println("There was an error  making a new file in the global conversation.");
                    return;
                }
            } catch (IOException e) {
                System.err.println("There was an error  making a new file in the global conversation.");
                return;
            }
        }
        YamlConfiguration configOrig = YamlConfiguration.loadConfiguration(file);
        NPCData npc = AllNPCs.getNPCFromUID(npcUID);
        if (npc == null)
            return;
        configOrig.set(YMLNpcNavigate.NPC_NAME, npc.name);
        configOrig.set(YMLNpcNavigate.NPC_UID, npc.uid);
        configOrig.set(YMLNpcNavigate.NPC_GAME_UID, npc.gameUID);
        configOrig.set(YMLNpcNavigate.STARTING_CONCLUSION, npc.getStartingConclusion());
        configOrig.set(YMLNpcNavigate.MAX_TIME_SINCE_LAST_TALK, npc.getMaxTimeSinceTalk());
        configOrig.createSection(YMLNpcNavigate.START_LOCATION);
        configOrig.createSection(YMLNpcNavigate.START_POSITION);

        configOrig.set(YMLNpcNavigate.VARS_TO_CONCLUSIONS, null);
        WriteNpcVarsToConclu.setConfig(configOrig.createSection(YMLNpcNavigate.VARS_TO_CONCLUSIONS), npc.getVarsToConclusion());
        WriteNpcConcluToConvo.setConfig(configOrig.createSection(YMLNpcNavigate.CONCLUSIONS_TO_CONVO), npc.getConclusionsToConvo());
        WriteNpcPlayers.write(configOrig.createSection(YMLNpcNavigate.PLAYERS_UID), npc.getPlayerDataMap());
        try {
            configOrig.save(file);
        } catch (IOException e) {
            System.err.println("There was an error writing the global conversation.");
        }
    }

    public int uid;
    public String name;
    public String gameUID;
    private int startingConclusion;
    private ArrayList<VarsConclusionMap> varsToConclusion;
    private Map<Integer, ConvoID> conclusionsToConvo;
    private Map<String, NPCPlayerData> playerDataMap;
    private long maxTimeSinceTalk;
}

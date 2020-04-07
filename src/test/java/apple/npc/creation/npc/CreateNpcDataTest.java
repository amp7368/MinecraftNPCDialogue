package apple.npc.creation.npc;

import apple.npc.creation.npc.info.NpcInfo;
import apple.npc.creation.npc.single.CreateNpcData;
import apple.npc.ymlNavigate.YMLTestNpc;

public class CreateNpcDataTest {

    public boolean createTest(String folder) {
        String globalName = YMLTestNpc.GLOBAL_NAME;
        NpcInfo npcInfo = new NpcInfo(globalName, YMLTestNpc.PERSONAL_UID, YMLTestNpc.GAME_UID, YMLTestNpc.STARTING_CONCLUSION, 300000);
        return CreateNpcData.create(folder, globalName, npcInfo);
    }
}

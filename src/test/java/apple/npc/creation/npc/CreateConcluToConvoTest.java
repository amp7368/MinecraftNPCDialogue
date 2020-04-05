package apple.npc.creation.npc;

import apple.npc.creation.npc.components.CreateConcluToConvo;
import apple.npc.creation.npc.info.ConcluToConvoInfo;
import apple.npc.creation.npc.info.NpcInfo;
import apple.npc.creation.npc.single.CreateNpcData;
import apple.npc.ymlNavigate.YMLTestNpc;

public class CreateConcluToConvoTest {
    public boolean createTest(String folder) {
        String globalName = YMLTestNpc.GLOBAL_NAME;
        int personalUID = YMLTestNpc.PERSONAL_UID;
        ConcluToConvoInfo concluToConvo = new ConcluToConvoInfo(YMLTestNpc.CONCLU_KEY, YMLTestNpc.CONCLU_CONVO_GLOBAL,
                YMLTestNpc.CONCLU_CONVO_LOCAL, YMLTestNpc.CONCLU_CONVO_UID);
        return CreateConcluToConvo.set(folder, String.valueOf(personalUID), globalName, concluToConvo);
    }
}

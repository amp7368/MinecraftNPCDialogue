package apple.npc.creation.from_scratch.npc;

import apple.npc.creation.from_scratch.npc.components.CreateConcluToConvo;
import apple.npc.creation.from_scratch.npc.info.ConcluToConvoInfo;
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

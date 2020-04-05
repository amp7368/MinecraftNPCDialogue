package apple.npc.creation;

import apple.npc.YMLTest;
import apple.npc.creation.category.CreateConvoLocal;
import apple.npc.creation.info.ConvoLocalInfo;

public class CreateConvoLocalTest {
    private String folder = "src\\NPCDialogue";

    public void createTest(String folder) {
        ConvoLocalInfo localInfo = new ConvoLocalInfo(YMLTest.CONVO_LOCAL_UID,YMLTest.CONVO_LOCAL_NAME);
        CreateConvoLocal.create(folder, YMLTest.CONVO_GLOBAL,localInfo);
    }
}

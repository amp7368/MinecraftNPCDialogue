package apple.npc.creation;

import apple.npc.YMLTest;
import apple.npc.creation.category.CreateConvoGlobal;

public class CreateConvoGlobalTest {

    public void createTest(String folder) {
        CreateConvoGlobal.initialize(folder);
        CreateConvoGlobal.create(YMLTest.CONVO_GLOBAL);
    }
}

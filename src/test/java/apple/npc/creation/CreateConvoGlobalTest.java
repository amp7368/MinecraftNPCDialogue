package apple.npc.creation;

import apple.npc.YMLTest;
import apple.npc.creation.category.CreateConvoGlobal;

public class CreateConvoGlobalTest {

    public boolean createTest(String folder) {
        CreateConvoGlobal.initialize(folder);
       return CreateConvoGlobal.create(YMLTest.CONVO_GLOBAL);
    }
}

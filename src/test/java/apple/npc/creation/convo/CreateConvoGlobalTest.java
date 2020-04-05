package apple.npc.creation.convo;

import apple.npc.ymlNavigate.YMLTestConvo;
import apple.npc.creation.category.CreateConvoGlobal;

public class CreateConvoGlobalTest {

    public boolean createTest(String folder) {
        CreateConvoGlobal.initialize(folder);
       return CreateConvoGlobal.create(YMLTestConvo.CONVO_GLOBAL);
    }
}

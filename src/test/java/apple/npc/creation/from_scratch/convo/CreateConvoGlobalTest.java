package apple.npc.creation.from_scratch.convo;

import apple.npc.ymlNavigate.YMLTestConvo;
import apple.npc.creation.from_scratch.convo.category.CreateConvoGlobal;

public class CreateConvoGlobalTest {

    public boolean createTest(String folder) {
       return CreateConvoGlobal.create(folder,YMLTestConvo.CONVO_GLOBAL);
    }
}

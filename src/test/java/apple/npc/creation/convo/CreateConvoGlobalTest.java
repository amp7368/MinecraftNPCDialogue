package apple.npc.creation.convo;

import apple.npc.ymlNavigate.YMLTestConvo;
import apple.npc.creation.convo.category.CreateConvoGlobal;

public class CreateConvoGlobalTest {

    public boolean createTest(String folder) {
       return CreateConvoGlobal.create(folder,YMLTestConvo.CONVO_GLOBAL);
    }
}

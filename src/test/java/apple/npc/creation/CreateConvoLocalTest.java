package apple.npc.creation;

import apple.npc.ymlNavigate.YMLTestConvo;
import apple.npc.creation.category.CreateConvoLocal;
import apple.npc.creation.info.ConvoLocalInfo;

public class CreateConvoLocalTest {
    public boolean createTest(String folder) {
        ConvoLocalInfo localInfo = new ConvoLocalInfo(YMLTestConvo.CONVO_LOCAL_UID, YMLTestConvo.CONVO_LOCAL_NAME);
        return CreateConvoLocal.create(folder, YMLTestConvo.CONVO_GLOBAL, localInfo);
    }
}

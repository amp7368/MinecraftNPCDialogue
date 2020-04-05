package apple.npc.creation;

import apple.npc.creation.components.CreatePostDefault;
import apple.npc.creation.components.CreatePostResponse;
import apple.npc.creation.info.ConvoRespPostInfo;
import apple.npc.creation.info.VariableChangeInfo;
import apple.npc.ymlNavigate.YMLTestConvo;

import java.util.ArrayList;
import java.util.List;

public class CreateConvoPostDefaultTest {
    public boolean createTest(String folder) {
        String globalName = YMLTestConvo.CONVO_GLOBAL;
        int localCategory = YMLTestConvo.CONVO_LOCAL_UID;
        int conversationUID = YMLTestConvo.CONVO_DATA_UID;
        int responseUID = YMLTestConvo.CONVO_RESPONSE_UID;
        List<VariableChangeInfo> variableChanges = new ArrayList<>();
        ConvoRespPostInfo postInfo = new ConvoRespPostInfo(responseUID, globalName, localCategory, conversationUID, variableChanges);

        return CreatePostDefault.set(folder, globalName, localCategory, conversationUID, responseUID, postInfo);
    }
}

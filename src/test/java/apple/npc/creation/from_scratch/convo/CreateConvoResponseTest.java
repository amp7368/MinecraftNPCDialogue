package apple.npc.creation.from_scratch.convo;

import apple.npc.ymlNavigate.YMLTestConvo;
import apple.npc.creation.from_scratch.convo.info.ConvoRespInfo;
import apple.npc.creation.from_scratch.convo.single.CreateConvoResponse;

import java.util.ArrayList;
import java.util.List;

public class CreateConvoResponseTest {
    public boolean createTest(String folder) {
        String globalName = YMLTestConvo.CONVO_GLOBAL;
        int localCategory = YMLTestConvo.CONVO_LOCAL_UID;
        int conversationUID = YMLTestConvo.CONVO_DATA_UID;
        List<String> text = new ArrayList<>();
        text.add(YMLTestConvo.CONVO_RESPONSE_TEXT_1);
        text.add(YMLTestConvo.CONVO_RESPONSE_TEXT_2);
        ConvoRespInfo respInfo = new ConvoRespInfo(YMLTestConvo.CONVO_RESPONSE_UID, text);
        return CreateConvoResponse.create(folder, globalName, localCategory, conversationUID, respInfo);
    }
}

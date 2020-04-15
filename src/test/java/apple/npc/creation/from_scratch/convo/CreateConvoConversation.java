package apple.npc.creation.from_scratch.convo;

import apple.npc.ymlNavigate.YMLTestConvo;
import apple.npc.creation.from_scratch.convo.info.ConvoDataInfo;
import apple.npc.creation.from_scratch.convo.single.CreateConvoData;

import java.util.ArrayList;
import java.util.List;

public class CreateConvoConversation {
    public boolean createTest(String folder) {
        String globalName = YMLTestConvo.CONVO_GLOBAL;
        int localCategory = YMLTestConvo.CONVO_LOCAL_UID;
        List<String> text = new ArrayList<>();
        text.add(YMLTestConvo.CONVO_DATA_TEXT_1);
        text.add(YMLTestConvo.CONVO_DATA_TEXT_2);
        ConvoDataInfo convoInfo = new ConvoDataInfo(YMLTestConvo.CONVO_DATA_UID, YMLTestConvo.CONVO_DATA_NAME, text);
        return CreateConvoData.create(folder, globalName, localCategory, convoInfo);
    }
}

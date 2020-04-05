package apple.npc.creation;

import apple.npc.YMLTest;
import apple.npc.creation.info.ConvoDataInfo;
import apple.npc.creation.single.CreateConvoData;

import java.util.ArrayList;
import java.util.List;

public class CreateConvoConversation {
    public boolean createTest(String folder) {
        String globalName = YMLTest.CONVO_GLOBAL;
        int localCategory = YMLTest.CONVO_LOCAL_UID;
        List<String> text = new ArrayList<>();
        text.add(YMLTest.CONVO_DATA_TEXT_1);
        text.add(YMLTest.CONVO_DATA_TEXT_2);
        ConvoDataInfo convoInfo = new ConvoDataInfo(YMLTest.CONVO_DATA_UID, YMLTest.CONVO_DATA_NAME, text);
        return CreateConvoData.create(folder, globalName, localCategory, convoInfo);
    }
}

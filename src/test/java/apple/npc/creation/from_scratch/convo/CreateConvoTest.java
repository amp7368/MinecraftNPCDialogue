package apple.npc.creation.from_scratch.convo;

import apple.npc.ymlNavigate.YMLTestConvo;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.junit.Test;

import java.io.File;

public class CreateConvoTest {
    private String folder = "src\\NPCDialogue";

    @Test
    public void createTest() {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER, File.separator, YMLTestConvo.CONVO_GLOBAL, ".yml"));
        file.delete();
        assert (new CreateConvoGlobalTest()).createTest(folder);
        assert (new CreateConvoLocalTest()).createTest(folder);
        assert (new CreateConvoConversation()).createTest(folder);
        assert (new CreateConvoResponseTest()).createTest(folder);
        assert (new CreateConvoPreReqTest()).createTest(folder);
        assert (new CreateConvoPostReqTest()).createTest(folder);
        assert (new CreateConvoPostDefaultTest()).createTest(folder);
    }
}

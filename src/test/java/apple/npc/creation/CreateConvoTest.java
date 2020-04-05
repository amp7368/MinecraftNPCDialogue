package apple.npc.creation;

import apple.npc.YMLTest;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.junit.Test;

import java.io.File;

public class CreateConvoTest {
    private String folder = "src\\NPCDialogue";

    @Test
    public void createTest() {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER, File.separator, YMLTest.CONVO_GLOBAL, ".yml"));
        assert file.delete();
        assert (new CreateConvoGlobalTest()).createTest(folder);
        assert (new CreateConvoLocalTest()).createTest(folder);
        assert (new CreateConvoConversation()).createTest(folder);
    }
}

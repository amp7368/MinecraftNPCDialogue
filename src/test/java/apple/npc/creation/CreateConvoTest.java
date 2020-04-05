package apple.npc.creation;

import org.junit.Test;

public class CreateConvoTest {
    private String folder = "src\\NPCDialogue";

    @Test
    public void createTest() {
        (new CreateConvoGlobalTest()).createTest(folder);
        (new CreateConvoLocalTest()).createTest(folder);
        (new CreateConvoConversation()).createTest(folder);
    }
}

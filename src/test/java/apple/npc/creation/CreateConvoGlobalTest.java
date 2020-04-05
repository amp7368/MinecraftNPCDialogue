package apple.npc.creation;

import apple.npc.creation.category.CreateConvoGlobal;
import org.junit.Test;

public class CreateConvoGlobalTest {
    private String dataFolder = "src\\NPCDialogue";

    @Test
    public void createTest() {
        CreateConvoGlobal.initialize(dataFolder);
        CreateConvoGlobal.create("test");
    }
}

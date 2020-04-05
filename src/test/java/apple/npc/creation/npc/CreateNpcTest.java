package apple.npc.creation.npc;

import apple.npc.creation.convo.*;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLTestConvo;
import apple.npc.ymlNavigate.YMLTestNpc;
import org.junit.Test;

import java.io.File;

public class CreateNpcTest {
    private String folder = "src\\NPCDialogue";

    @Test
    public void createTest() {
        File file = new File(String.format("%s%s%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.NPC_FOLDER, File.separator, YMLTestNpc.PERSONAL_UID, ",", YMLTestNpc.GLOBAL_NAME, ".yml"));
        file.delete();
        assert (new CreateNpcDataTest()).createTest(folder);
        assert (new CreateVarsToConcluTest()).createTest(folder);
        assert (new CreateConcluToConvoTest()).createTest(folder);
    }
}

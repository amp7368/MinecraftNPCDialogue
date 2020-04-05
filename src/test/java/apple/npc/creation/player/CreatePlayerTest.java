package apple.npc.creation.player;

import apple.npc.creation.npc.CreateConcluToConvoTest;
import apple.npc.creation.npc.CreateNpcDataTest;
import apple.npc.creation.npc.CreateVarsToConcluTest;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLTestNpc;
import apple.npc.ymlNavigate.YMLTestPlayer;
import org.junit.Test;

import java.io.File;

public class CreatePlayerTest {
    private String folder = "src\\NPCDialogue";

    @Test
    public void createTest() {
        File file = new File(String.format("%s%s%s%s%s%s", folder, File.separator, YMLFileNavigate.PLAYER_FOLDER,
                File.separator, YMLTestPlayer.PLAYER_UID, ".yml"));
        file.delete();
        assert (new CreatePlayerDataTest()).createTest(folder);
        assert (new CreatePlayerDataVarsTest()).createTest(folder);
    }
}

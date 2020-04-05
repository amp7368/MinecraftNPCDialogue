package apple.npc.data.all;

import org.junit.Test;

import java.io.File;

public class AllPlayerTest {
    private File dataFolder = new File("src\\NPCDialogue");
    @Test
    public void conversationReading(){
        AllPlayers.initialize(dataFolder);
    }
}

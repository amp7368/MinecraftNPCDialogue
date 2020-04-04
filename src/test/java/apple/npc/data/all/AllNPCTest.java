package apple.npc.data.all;

import org.junit.Test;

import java.io.File;

public class AllNPCTest {
    private File dataFolder = new File("src\\test\\NPCDialogue");

    @Test
    public void conversationReading() {
        AllNPCs.initialize(dataFolder);
    }
}

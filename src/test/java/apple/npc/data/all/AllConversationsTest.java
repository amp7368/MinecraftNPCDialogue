package apple.npc.data.all;

import org.junit.Test;

import java.io.File;

public class AllConversationsTest {
    private File dataFolder = new File("src\\test\\NPCDialogue");
    @Test
    public void conversationReading(){
        AllConversations.initialize(dataFolder);
    }
}

package apple.npc;

import apple.npc.commands.CreateCommand;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.all.AllPlayers;
import apple.npc.data.single.PlayerData;
import apple.npc.listeners.ClickListener;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCDialogueMain extends JavaPlugin {
    @Override
    public void onEnable() {
        new CreateCommand(this);
        new ClickListener(this);
        AllNPCs.initialize(this);
        AllPlayers.initialize(this);
        System.out.println("[NPCDialogue] enabled");
    }
}

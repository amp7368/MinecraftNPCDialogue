package apple.npc;

import apple.npc.commands.CreateCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.all.AllPlayers;
import apple.npc.listeners.ClickListener;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCDialogueMain extends JavaPlugin {
    @Override
    public void onLoad() {
        AllNPCs.initialize(this.getDataFolder());
        AllPlayers.initialize(this.getDataFolder());
        AllConversations.initialize(this.getDataFolder());
        System.out.println("[NPCDialogue] loaded");
    }

    @Override
    public void onEnable() {
        new CreateCommand(this);
        new ClickListener(this);
        AllNPCs.initialize(this.getDataFolder());
        AllPlayers.initialize(this.getDataFolder());
        AllConversations.initialize(this.getDataFolder());
        System.out.println("[NPCDialogue] enabled");
    }
}

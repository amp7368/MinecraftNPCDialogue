package apple.npc;

import apple.npc.commands.Commands;
import apple.npc.commands.CreateRedirect;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.all.AllPlayers;
import apple.npc.listeners.ClickListener;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCDialogueMain extends JavaPlugin {
    @Override
    public void onEnable() {
        Commands.initialize(this);
        ActionBar.initialize(this);
        AllNPCs.initialize(this.getDataFolder());
        AllPlayers.initialize(this.getDataFolder());
        AllConversations.initialize(this.getDataFolder());
        new ClickListener(this);
        System.out.println("[NPCDialogue] enabled");
    }
}

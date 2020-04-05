package apple.npc.listeners;

import apple.npc.NPCDialogueMain;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.all.AllPlayers;
import apple.npc.data.single.NPCData;
import apple.npc.data.single.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class ClickListener implements Listener {
    JavaPlugin plugin;

    public ClickListener(NPCDialogueMain plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void rightClickListener(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        UUID uuid = entity.getUniqueId();
        Player player = event.getPlayer();
        player.getUniqueId();
        if (entity.getType() == EntityType.ARMOR_STAND) {
            doConversation(entity, player);
            player.sendMessage("armorStand");
        }
        player.sendMessage(uuid.toString());
        player.sendMessage("hi");
    }

    private void doConversation(Entity entity, Player p) {
        NPCData npc = AllNPCs.getNPC(entity.getUniqueId().toString());
        PlayerData player = AllPlayers.getPlayer(p.getUniqueId().toString());
        npc.doConversation(player,p);
    }
}

package apple.npc.listeners;

import apple.npc.NPCDialogueMain;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.all.AllPlayers;
import apple.npc.data.npc.NPCData;
import apple.npc.data.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class ClickListener implements Listener {
    private HashMap<String, Long> cooldown = new HashMap<>();

    public ClickListener(NPCDialogueMain plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void rightClickListener(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        UUID uuid = entity.getUniqueId();
        Player player = event.getPlayer();
        System.out.println(uuid.toString());
        if (AllNPCs.hasGameUID(uuid.toString())) {
            doConversation(entity, player);
            event.setCancelled(true);
        }
    }

    private void doConversation(Entity entity, Player p) {
        String playerUID = p.getUniqueId().toString();
        // don't let the player spam the npc
        if (cooldown.containsKey(playerUID) && System.currentTimeMillis() - cooldown.get(playerUID) < 3000) {
            return;
        }
        cooldown.put(playerUID, System.currentTimeMillis());


        NPCData npc = AllNPCs.getNPCFromUID(entity.getUniqueId().toString());
        PlayerData player = AllPlayers.getPlayer(p.getUniqueId().toString());
        if (npc != null)
            npc.doEntireConversation(player, p);
    }
}

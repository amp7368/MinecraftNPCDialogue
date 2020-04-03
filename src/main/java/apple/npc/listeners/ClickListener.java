package apple.npc.listeners;

import apple.npc.NPCDialogueMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
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

        player.sendMessage(uuid.toString());
        player.sendMessage("hi");
    }
}

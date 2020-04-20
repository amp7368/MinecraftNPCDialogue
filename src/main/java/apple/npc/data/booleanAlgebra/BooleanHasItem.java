package apple.npc.data.booleanAlgebra;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;


public class BooleanHasItem implements Evaluateable {
    private boolean isNot;
    private String localName;
    private String displayName;
    private String material;
    private int slot;
    private int trackingType;

    public BooleanHasItem(boolean isNot, ItemStack item, int trackingType) {
        this.isNot = isNot;
        this.trackingType = trackingType;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            localName = "";
            displayName = "";
        } else {
            localName = meta.getLocalizedName();
            displayName = meta.getDisplayName();
        }
        material = item.getType().toString();
        slot = 0;
    }


    @Override
    public boolean evaluate(Player player, int currentConclusion, long timeLastTalked) {
        @NotNull ItemStack[] extra = player.getInventory().getExtraContents();
        for (ItemStack itemInInv : extra) {
            if (checkItem(itemInInv))
                return true;
        }
        @NotNull ItemStack[] inv = player.getInventory().getContents();
        for (ItemStack itemInInv : inv) {
            if (checkItem(itemInInv))
                return true;
        }
        return false;
    }

    private boolean checkItem(ItemStack itemInInv) {
        if(itemInInv == null)
            return false;
        if (itemInInv.getType().toString().equals(material)) {
            ItemMeta im;
            switch (trackingType) {
                case 0:
                    im = itemInInv.getItemMeta();
                    if (im == null) break;
                    if (im.getDisplayName().equals(displayName)) {
                        return true;
                    }
                    break;
                case 1:
                    im = itemInInv.getItemMeta();
                    if (im == null) break;
                    if (im.getLocalizedName().equals(displayName)) {
                        return true;
                    }
                    break;
                case 2:
                    im = itemInInv.getItemMeta();
                    if (im == null) break;
                    if (im.getDisplayName().equals(displayName)) {
                        return true;
                    }
                    break;
                case 3:
                    im = itemInInv.getItemMeta();
                    if (im == null) break;
                    if (im.getDisplayName().equals(displayName) && im.getLocalizedName().equals(localName)) {
                        return true;
                    }
                    break;

            }
        }
        return false;
    }
}

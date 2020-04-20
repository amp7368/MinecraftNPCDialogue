package apple.npc.data.booleanAlgebra;

import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;
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

    public BooleanHasItem(ConfigurationSection config) {
        this.isNot = config.getBoolean(YMLBooleanNavigate.IS_NOTED);
        this.localName = config.getString(YMLBooleanNavigate.ITEM_LOCAL);
        this.displayName = config.getString(YMLBooleanNavigate.ITEM_DISPLAY);
        this.material = config.getString(YMLBooleanNavigate.ITEM_MATERIAL);
        this.slot = config.getInt(YMLBooleanNavigate.ITEM_SLOT);
        this.trackingType = config.getInt(YMLBooleanNavigate.TRACKING_TYPE);
    }


    @Override
    public boolean evaluate(Player player, int currentConclusion, long timeLastTalked) {
        @NotNull ItemStack[] extra = player.getInventory().getExtraContents();
        for (ItemStack itemInInv : extra) {
            if (checkItem(itemInInv))
                return !isNot;
        }
        @NotNull ItemStack[] inv = player.getInventory().getContents();
        for (ItemStack itemInInv : inv) {
            if (checkItem(itemInInv))
                return !isNot;
        }
        return isNot;
    }

    private boolean checkItem(ItemStack itemInInv) {
        if (itemInInv == null)
            return false;
        if (itemInInv.getType().toString().equals(material)) {
            ItemMeta im;
            switch (trackingType) {
                case 0:
                    return true;
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

    public boolean isNot() {
        return isNot;
    }

    public String getLocal() {
        return localName;
    }

    public String getDisplay() {
        return displayName;
    }

    public String getMaterial() {
        return material;
    }

    public int getSlot() {
        return slot;
    }

    public int getTracking() {
        return trackingType;
    }
}

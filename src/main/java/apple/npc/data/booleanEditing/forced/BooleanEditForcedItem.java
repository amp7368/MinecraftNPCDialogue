package apple.npc.data.booleanEditing.forced;

import apple.npc.data.booleanAlgebra.BooleanHasItem;
import apple.npc.data.booleanAlgebra.Evaluateable;
import org.bukkit.inventory.ItemStack;

public class BooleanEditForcedItem implements BooleanEditForced {
    private boolean isNot;
    private ItemStack item;
    private int trackingType;
    private BooleanEditForced parent;
    private int name;

    public BooleanEditForcedItem(boolean isNot, ItemStack item, int trackingType, BooleanEditForced parent, int name) {
        this.isNot = isNot;
        this.item = item;
        this.trackingType = trackingType;
        this.parent = parent;
        this.name = name;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Evaluateable toFinished() {
        return new BooleanHasItem(isNot, item, trackingType);
    }

    @Override
    public int nextExp() {
        return -1;
    }

    @Override
    public BooleanEditForced getLeftMost() {
        return null;
    }

    @Override
    public int getName() {
        return name;
    }

    @Override
    public BooleanEditForced getParent() {
        return parent;
    }

    @Override
    public int getBiggestName() {
        return name;
    }
}

package dslite.world.player.inventory;

import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemInfo;
import dslite.world.entity.item.ItemType;

/**
 * Represents a slot in the player's inventory
 */
public final class Slot {

    private Item storedItem;
    private ItemType storedItemType;
    private int itemCount;
    private byte stackSize;
    private boolean full;

    Slot() {
        init();
    }

    void init() {
        stackSize = Inventory.STACK_SIZE;
        storedItem = null;
        storedItemType = null;
        full = false;
        itemCount = 0;
    }

    /**
     * Tries to add a specific quantity of the stored item to the slot.
     *
     * @param count the number of items to add
     * @return number of items successfully added
     */
    public int add(int count) {
        int initialSize = getItemCount();
        if (count > 0) {
            itemCount = Math.min(stackSize, itemCount + count);
        } else if (count < 0) {
            itemCount = Math.max(0, itemCount + count);
        }

        update();
        return Math.abs(itemCount - initialSize);
    }

    /**
     * Removes the stored {@link Item} if the quanity is {@code 0},<br/>
     * or sets {@code full} to {@code true} if the slot is full.
     */
    private void update() {
        if (getItemCount() <= 0) {
            init();
        } else if (getItemCount() >= stackSize) {
            itemCount = stackSize;
            full = true;
        }
    }

    /**
     * Sets the currently stored item by the slot.
     * @param itemType the {@link ItemType} of the stored item.
     */
    void setStoredItem(ItemType itemType) {
        init();
        storedItemType = itemType;
        storedItem = ItemInfo.getItem(itemType);
        if (!itemType.isStackable()) {
            stackSize = 1;
        }
    }

    public ItemType getStoredItemType() {
        return storedItemType;
    }

    public Item getStoredItem() {
        return storedItem;
    }

    public int getItemCount() {
        return itemCount;
    }

    public boolean isFull() {
        return full;
    }

}

package dslite.world.player.inventory;

import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemInfo;
import dslite.world.entity.item.ItemType;

/**
 * A játékos eszköztárában található Slotot
 * reprezentáló osztály.
 */
public final class Slot {

    private Item storedItem;
    private ItemType storedItemType;
    private int itemCount;
    private byte stackSize;
    private boolean full;

    protected Slot() {
        init();
    }

    /**
     * Beállítja a slotot a kezdeti értékre ("üres").
     */
    protected void init() {
        stackSize = Inventory.STACK_SIZE;
        storedItem = null;
        storedItemType = null;
        full = false;
        itemCount = 0;
    }

    /**
     * Megpróbál hozzáadni a slothoz adott mennyiségű itemet.
     *
     * @param count A hozzáadni kívánt mennyiség.
     * @return Hány darabot sikerült ténylegesen hozzáadni.
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
     * Megvizsgálja, hogy az eltárolt mennyiség kívül esik-e a Slot méretén,
     * és ha kívül esik, beállítja a méretet ennek megfelelően.
     * Ha a méret 0, vagy annál kisebb, akkor törli a slotot.
     */
    private void update() {
        if (getItemCount() <= 0) {
            init();
        } else if (getItemCount() >= stackSize) {
            itemCount = stackSize;
            full = true;
        }
    }

    protected void setStoredItem(ItemType itemType) {
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

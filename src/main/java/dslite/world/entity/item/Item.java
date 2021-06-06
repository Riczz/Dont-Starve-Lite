package dslite.world.entity.item;

/**
 * Represents an item in the player's inventory.
 *
 * @see ItemInfo
 */
public abstract class Item {

    private final ItemType type;

    public Item(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}

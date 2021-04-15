package dslite.world.entity.item;

/**
 * A játékos Inventoryjában lévő Itemet írja le.
 */
public abstract class Item {

    private final ItemType type;

    /**
     * Az Item konstruktora.
     *
     * @param type Az Item típusa
     * @see ItemType
     */
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

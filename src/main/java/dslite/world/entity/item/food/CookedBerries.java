package dslite.world.entity.item.food;

import dslite.world.entity.item.ItemType;

/**
 * A főtt bogyó Itemet leíró osztály.
 */
public final class CookedBerries extends Food {
    public CookedBerries() {
        super(ItemType.BERRIES_COOKED, null, 8.0f, 0.0f, 2.0f);
    }
}

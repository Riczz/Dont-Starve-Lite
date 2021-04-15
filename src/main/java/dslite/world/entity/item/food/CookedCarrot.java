package dslite.world.entity.item.food;

import dslite.world.entity.item.ItemType;

/**
 * A főtt répa Itemet leíró osztály.
 */
public final class CookedCarrot extends Food {
    public CookedCarrot() {
        super(ItemType.CARROT_COOKED, null, 8.0f, 2.0f, 3.0f);
    }
}

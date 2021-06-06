package dslite.world.entity.item.food;

import dslite.world.entity.item.ItemType;

public final class Berries extends Food {

    public Berries() {
        super(ItemType.BERRIES, new CookedBerries(), 10.0f, -5.0f, 1.0f);
    }

}

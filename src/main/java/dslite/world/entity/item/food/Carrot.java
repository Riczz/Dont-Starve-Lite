package dslite.world.entity.item.food;

import dslite.world.entity.item.ItemType;

public final class Carrot extends Food {

    public Carrot() {
        super(ItemType.CARROT, new CookedCarrot(), 10.0f, 0.0f, 1.0f);
    }

}

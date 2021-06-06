package dslite.world.entity.item.survival;

import dslite.world.entity.item.Craftable;
import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;

import java.util.Map;

public final class Campfire extends Item implements Craftable {

    public Campfire() {
    super(ItemType.CAMPFIRE);
    }

    public Map<ItemType, Integer> getRequirements() {
        return Map.of(ItemType.LOG, 2, ItemType.ROCKS, 4, ItemType.CUTGRASS, 2);
    }

}

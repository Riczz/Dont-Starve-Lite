package dslite.world.entity.item.tool;

import dslite.world.entity.item.Craftable;
import dslite.world.entity.item.ItemType;

import java.util.Map;

public final class Axe extends Tool implements Craftable {

    public Axe() {
        super(ItemType.AXE, (byte) 10);
    }

    @Override
    public Map<ItemType, Integer> getRequirements() {
        return Map.of(ItemType.TWIGS, 1, ItemType.FLINT, 1);
    }

}

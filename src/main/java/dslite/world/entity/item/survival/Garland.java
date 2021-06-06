package dslite.world.entity.item.survival;

import dslite.controllers.GameController;
import dslite.world.World;
import dslite.world.Updatable;
import dslite.world.entity.item.Craftable;
import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;

import java.util.Map;

public final class Garland extends Item implements Craftable, Updatable {

    //Durability
    private int durability = 15 * World.DAY_LENGTH;

    public Garland() {
        super(ItemType.GARLAND);
    }

    @Override
    public void update() {
        if (--durability <= 0) {
            GameController.getPlayer().getInventory().removeItem(this);
        }
    }

    public Map<ItemType, Integer> getRequirements() {
        return Map.of(ItemType.PETAL, 10);
    }
}

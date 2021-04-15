package dslite.world.entity.item.survival;

import dslite.controllers.GameController;
import dslite.world.World;
import dslite.world.Updatable;
import dslite.world.entity.item.Craftable;
import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;

import java.util.Map;

/**
 * A virágkoszorú Itemet leíró osztály
 */
public final class Garland extends Item implements Craftable, Updatable {

    private int health = 15 * World.DAY_LENGTH;

    public Garland() {
        super(ItemType.GARLAND);
    }

    /**
     * Ha 15 nap eltelt a készítése óta, törli magát az Inventoryból.
     */
    @Override
    public void update() {
        if (--health <= 0) {
            GameController.getPlayer().getInventory().removeItem(this);
        }
    }

    public Map<ItemType, Integer> getRequirements() {
        return Map.of(ItemType.PETAL, 10);
    }
}

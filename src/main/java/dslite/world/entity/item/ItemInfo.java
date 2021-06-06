package dslite.world.entity.item;

import dslite.world.entity.item.crafting.*;
import dslite.world.entity.item.food.Berries;
import dslite.world.entity.item.food.Carrot;
import dslite.world.entity.item.food.CookedBerries;
import dslite.world.entity.item.food.CookedCarrot;
import dslite.world.entity.item.survival.Campfire;
import dslite.world.entity.item.survival.Garland;
import dslite.world.entity.item.tool.Axe;
import dslite.world.entity.item.tool.Pickaxe;

/**
 * Class for getting actual objects for specified {@linkplain ItemType}s.
 *
 * @see Item
 */
public final class ItemInfo {

    private ItemInfo() {
    }

    public static Item getItem(ItemType type) {
        switch (type) {
            case AXE: return new Axe();
            case BERRIES: return new Berries();
            case BERRIES_COOKED: return new CookedBerries();
            case CAMPFIRE: return new Campfire();
            case CARROT: return new Carrot();
            case CARROT_COOKED: return new CookedCarrot();
            case CUTGRASS: return new CutGrass();
            case FLINT: return new Flint();
            case GARLAND: return new Garland();
            case GOLD: return new Gold();
            case LOG: return new Log();
            case PETAL: return new Petal();
            case PICKAXE: return new Pickaxe();
            case ROCKS: return new Rocks();
            case TWIGS: return new Twigs();
            default: return null;
        }
    }
}

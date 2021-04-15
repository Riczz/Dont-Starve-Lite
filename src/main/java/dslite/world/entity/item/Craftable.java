package dslite.world.entity.item;

import java.util.Map;

/**
 * Craftolható interfész.
 * Lekérdezhetők a craftoláshoz szükséges Item típusok, és az azokból szükséges darabszám.
 */
public interface Craftable {
    Map<ItemType, Integer> getRequirements();
}

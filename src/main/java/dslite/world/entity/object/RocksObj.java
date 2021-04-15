package dslite.world.entity.object;

import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

/**
 * Az eldobott kő GameObjectet leíró osztály.
 */
public final class RocksObj extends GameObject {

    public RocksObj() {
        super(ObjectType.ROCKS);

    }

    @Override
    public void interact(Player player) {
        player.getInventory().addItem(ItemType.ROCKS, getQuantity());

        TileWithObject t = getTile();
        player.getMap().setTileAtPos(t.getPos(), t.getType());
    }
}

package dslite.world.entity.object;

import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

/**
 * Az eldobott aranyrög GameObjectet leíró osztály.
 */
public final class GoldObj extends GameObject {
    public GoldObj() {
        super(ObjectType.GOLD);
    }

    @Override
    public void interact(Player player) {

        if (!player.getInventory().addItem(ItemType.GOLD,getQuantity())) return;

        TileWithObject t = getTile();
        player.getMap().setTileAtPos(t.getPos(),t.getType());
    }
}

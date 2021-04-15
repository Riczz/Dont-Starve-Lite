package dslite.world.entity.object;

import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

/**
 * A Répa GameObject-et leíró osztály.
 */
public final class CarrotObj extends GameObject {
    public CarrotObj() {
        super(ObjectType.CARROT);
    }

    @Override
    public void interact(Player player) {
        if (!player.getInventory().addItem(ItemType.CARROT, getQuantity())) return;

        TileWithObject t = getTile();
        player.addActionPoints((byte) -1);
        player.getMap().setTileAtPos(t.getPos(), t.getType());
    }

}

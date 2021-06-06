package dslite.world.entity.object;

import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

public final class FlintObj extends GameObject {
    public FlintObj() {
        super(ObjectType.FLINT);
    }

    @Override
    public void interact(Player player) {
        if (!player.getInventory().addItem(ItemType.FLINT, getQuantity())) return;

        TileWithObject t = getTile();
        player.addActionPoints((byte) -1);
        player.getMap().setTileAtPos(t.getPos(), t.getType());
    }
}

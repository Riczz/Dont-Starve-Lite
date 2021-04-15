package dslite.world.entity.object;

import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;
import dslite.world.entity.item.tool.Tool;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

/**
 * A Sivatagi szikla GameObject-et leíró osztály.
 */
public final class BoulderFlintless extends GameObject {

    protected BoulderFlintless() {
        super(ObjectType.BOULDER_FLINTLESS);
    }

    @Override
    public void interact(Player player) {
        Item equippedItem = player.getEquippedItem();

        if (!(equippedItem instanceof Tool && equippedItem.getType() == ItemType.PICKAXE)) return;

        TileWithObject t = getTile();
        if (player.getInventory().addItem(ItemType.ROCKS, getQuantity())) {
            player.addActionPoints((byte) -3);
            ((Tool) equippedItem).setDurability((byte) -1);
            player.getMap().setTileAtPos(t.getPos(), t.getType());
        }
    }
}

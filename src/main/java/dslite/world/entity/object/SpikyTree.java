package dslite.world.entity.object;

import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;
import dslite.world.entity.item.tool.Tool;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

public final class SpikyTree extends GameObject {

    private byte health = 2;

    protected SpikyTree() {
        super(ObjectType.SPIKYTREE);
    }

    @Override
    public void interact(Player player) {

        Item equippedItem = player.getEquippedItem();

        if (!(equippedItem instanceof Tool && equippedItem.getType() == ItemType.AXE)) return;

        TileWithObject t = getTile();
        if (player.getInventory().addItem(ItemType.TWIGS, getQuantity())) {
            player.addActionPoints((byte) -3);
            ((Tool) equippedItem).setDurability((byte) -1);
            if (--health <= 0) player.getMap().setTileAtPos(t.getPos(), t.getType());
        }
    }
}

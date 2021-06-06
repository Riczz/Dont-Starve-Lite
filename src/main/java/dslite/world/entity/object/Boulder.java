package dslite.world.entity.object;

import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;
import dslite.world.entity.item.tool.Tool;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

public final class Boulder extends GameObject {

    private byte health = 2;

    protected Boulder() {
        super(ObjectType.BOULDER);
    }

    @Override
    public void interact(Player player) {
        Item equippedItem = player.getEquippedItem();

        if (!(equippedItem instanceof Tool && equippedItem.getType() == ItemType.PICKAXE)) return;

        TileWithObject t = getTile();
        if (player.getInventory().addItem(ItemType.ROCKS, getQuantity())) {
            player.addActionPoints((byte) -4);
            ((Tool) equippedItem).setDurability((byte) -1);
            if (--health <= 0) player.getMap().setTileAtPos(t.getPos(), t.getType());
        }
    }
}

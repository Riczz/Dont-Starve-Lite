package dslite.world.entity.object;

import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;
import dslite.world.entity.item.tool.Tool;
import dslite.world.player.Player;
import dslite.world.player.inventory.Inventory;
import dslite.world.tiles.TileWithObject;

/**
 * Az arany érc GameObjectet leíró osztály.
 */
public final class GoldVein extends GameObject {
    protected GoldVein() {
        super(ObjectType.GOLDVEIN);
    }

    @Override
    public void interact(Player player) {
        Item equippedItem = player.getEquippedItem();

        if (!(equippedItem instanceof Tool && equippedItem.getType() == ItemType.PICKAXE)) return;

        TileWithObject t = getTile();
        Inventory inv = player.getInventory();
        if (inv.addItem(ItemType.ROCKS, getQuantity()) && inv.addItem(ItemType.GOLD, getQuantity())) {
            player.addActionPoints((byte) -4);
            ((Tool) equippedItem).setDurability((byte) -1);
            player.getMap().setTileAtPos(t.getPos(), t.getType());
        }
    }
}

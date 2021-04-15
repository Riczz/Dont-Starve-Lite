package dslite.world.entity.object;

import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;

/**
 * A Bokor GameObject-et leíró osztály.
 */
public final class Bush extends GameObject {
    protected Bush() {
        super(ObjectType.BUSH);
    }

    @Override
    public void interact(Player player) {
        if (!player.getInventory().addItem(ItemType.BERRIES, getQuantity())) return;

        player.addActionPoints((byte) -1);
        getTile().setObject(new BushPicked());
    }
}

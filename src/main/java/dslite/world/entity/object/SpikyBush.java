package dslite.world.entity.object;

import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;

/**
 * A szúrós bokor GameObjectet leíró osztály.
 */
public final class SpikyBush extends GameObject {

    protected SpikyBush() {
        super(ObjectType.SPIKYBUSH);
    }

    @Override
    public void interact(Player player) {
        if (!player.getInventory().addItem(ItemType.TWIGS, getQuantity())) return;

        player.addHealth(-3.0f);
        player.addActionPoints((byte) -1);
        getTile().setObject(new SpikyBushPicked());

    }
}

package dslite.world.entity.object;

import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;

/**
 * A fű GameObjectet leíró osztály.
 */
public final class Grass extends GameObject {
    protected Grass() {
        super(ObjectType.GRASS);
    }

    @Override
    public void interact(Player player) {
        if (!player.getInventory().addItem(ItemType.CUTGRASS, getQuantity())) return;

        player.addActionPoints((byte) -1);
        getTile().setObject(new GrassPicked());
    }
}

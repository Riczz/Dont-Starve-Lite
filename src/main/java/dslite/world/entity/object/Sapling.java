package dslite.world.entity.object;

import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;

public final class Sapling extends GameObject {
    protected Sapling() {
        super(ObjectType.SAPLING);
    }

    @Override
    public void interact(Player player) {
        if (!player.getInventory().addItem(ItemType.TWIGS, getQuantity())) return;

        player.addActionPoints((byte) -1);
        getTile().setObject(new SaplingPicked());
    }
}

package dslite.world.entity.object;

import dslite.world.player.Player;

/**
 * A leszedett Sapling GameObjectet leíró osztály.
 */
public final class SaplingPicked extends GameObject {

    protected SaplingPicked() {
        super(ObjectType.SAPLING_PICKED);
    }

    @Override
    public void interact(Player player) {
    }
}

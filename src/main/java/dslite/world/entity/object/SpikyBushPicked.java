package dslite.world.entity.object;

import dslite.world.player.Player;

/**
 * A leszedett szúrós bokor GameObjectet leíró osztály.
 */
public final class SpikyBushPicked extends GameObject {

    protected SpikyBushPicked() {
        super(ObjectType.SPIKYBUSH_PICKED);
    }

    @Override
    public void interact(Player player) {
    }
}

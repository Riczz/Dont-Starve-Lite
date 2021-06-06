package dslite.world.entity.object;

import dslite.world.player.Player;

public final class SpikyBushPicked extends GameObject {

    protected SpikyBushPicked() {
        super(ObjectType.SPIKYBUSH_PICKED);
    }

    @Override
    public void interact(Player player) {
    }
}

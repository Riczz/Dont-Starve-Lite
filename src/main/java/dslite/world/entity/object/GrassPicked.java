package dslite.world.entity.object;

import dslite.world.player.Player;

public final class GrassPicked extends GameObject {

    protected GrassPicked() {
        super(ObjectType.GRASS_PICKED);
    }

    @Override
    public void interact(Player player) {
    }

}

package dslite.world.entity.object;

import dslite.world.player.Player;

/**
 * A leszedett fű GameObjectet leíró osztály.
 */
public final class GrassPicked extends GameObject {

    protected GrassPicked() {
        super(ObjectType.GRASS_PICKED);
    }

    @Override
    public void interact(Player player) {
    }

}

package dslite.world.entity.object;

import dslite.world.player.Player;

/**
 * A Leszedett bokor GameObject-et leíró osztály.
 */
public final class BushPicked extends GameObject {

    protected BushPicked() {
        super(ObjectType.BUSH_PICKED);
    }

    @Override
    public void interact(Player player) {
    }

}

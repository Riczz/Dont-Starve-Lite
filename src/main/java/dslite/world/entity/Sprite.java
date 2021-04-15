package dslite.world.entity;

/**
 * A játékban lévő Sprite-okat leíró osztály.
 *
 * @see Textures
 */
public final class Sprite {

    private final int index;

    public Sprite(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

package dslite.world.entity.object;

import dslite.world.entity.Sprite;

/**
 * Az GameObjectek típusaihoz tartozó enum.
 * Mindegyik objektumhoz hozzárendeli a Sprite-jait index alapján.
 *
 * @see Sprite
 * @see dslite.world.entity.Textures
 * @see GameObject
 */
public enum ObjectType {
    AXE(40),
    PICKAXE(41),
    BOULDER(2),
    BOULDER_FLINTLESS(3),
    BUSH(4),
    BUSH_PICKED(5),
    CARROT(33),
    CUTGRASS(24),
    EVERGREEN(6),
    FLINT(25),
    FLOWER(8, 9, 10, 11, 12, 13, 14, 15),
    GARLAND(39),
    GOLD(26),
    GOLDVEIN(16),
    GRASS(17),
    GRASS_PICKED(18),
    LOG(27),
    LUMPYEVERGREEN(7),
    PETALS(28),
    ROCKS(29),
    SAPLING(19),
    SAPLING_PICKED(20),
    SPIKYBUSH(21),
    SPIKYBUSH_PICKED(22),
    SPIKYTREE(23),
    TWIGS(30),
    CAMPFIRE(38);

    private final int[] indexes;
    private final Sprite sprite;

    /**
     * Az objektumtípus konstruktora.
     * Egy típushoz tartozhat többféle Sprite is.
     *
     * @param spriteIndex Az objektumhoz tartozó Sprite index(ek)
     * @see Flower
     */
    ObjectType(int... spriteIndex) {
        indexes = spriteIndex;
        sprite = new Sprite(spriteIndex[0]);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int[] getSpriteIndexes() {
        return indexes;
    }
}

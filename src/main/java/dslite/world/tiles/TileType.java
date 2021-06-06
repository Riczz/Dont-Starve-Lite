package dslite.world.tiles;

import javafx.scene.paint.Color;

/**
 * Enum that stores the possible Tile types.
 */
public enum TileType {
    WATER(Color.AQUA, true),
    GRASS(Color.YELLOWGREEN, false),
    FOREST(Color.FORESTGREEN, false),
    ROCKY(Color.GAINSBORO, false),
    MARSH(Color.THISTLE, false),
    SAVANNA(Color.GOLD, false),
    SAND(Color.MOCCASIN, false);

    private final Color color;
    private final boolean isSolid;

    /**
     * Constructor for a tile type.
     * @param color color of the {@link Tile}
     * @param isSolid {@code true} if solid, {@code false} if not
     */
    TileType(Color color, boolean isSolid) {
        this.color = color;
        this.isSolid = isSolid;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public Color getColor() {
        return color;
    }

}

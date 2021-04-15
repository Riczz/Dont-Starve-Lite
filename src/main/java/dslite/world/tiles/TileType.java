package dslite.world.tiles;

import javafx.scene.paint.Color;

/**
 * A játékban található mezőket leíró enum.
 */
public enum TileType {
    WATER(Color.AQUA, true),
    GRASS(Color.YELLOWGREEN, false),
    FOREST(Color.FORESTGREEN, false),
    ROCKY(Color.GAINSBORO, false),
    MARSH(Color.THISTLE, false),
    SAVANNA(Color.GOLD, false),
    SAND(Color.MOCCASIN, false);

    Color color;
    boolean isSolid;

    /**
     * A mező típus konstruktora.
     * @param color A mező színe
     * @param isSolid Szolid-e
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

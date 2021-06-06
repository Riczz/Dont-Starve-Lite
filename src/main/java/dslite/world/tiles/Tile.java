package dslite.world.tiles;

import dslite.world.Drawable;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class describing a game field without any stored objects.
 *
 * @see TileWithObject
 */
public class Tile implements Drawable {

    private TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public void draw(GraphicsContext gc, int i, int j) {
        gc.setFill(type.getColor());
        gc.fillRect(i, j, 1.0, 1.0);
    }

    public void setType(TileType t) {
        this.type = t;
    }

    public TileType getType() {
        return type;
    }

}

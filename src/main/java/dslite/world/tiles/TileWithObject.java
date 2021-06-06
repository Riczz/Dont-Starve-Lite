package dslite.world.tiles;

import dslite.world.biomes.Point;
import dslite.world.entity.Sprite;
import dslite.world.entity.Textures;
import dslite.world.entity.object.GameObject;
import dslite.world.entity.object.ObjectInfo;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class describing a game field with a specific {@link GameObject} inside.
 * Also stores the tile's position on the map.
 *
 * @see Tile
 * @see GameObject
 */
public final class TileWithObject extends Tile {

    private GameObject object;
    private final Point pos;

    /**
     * Constructor for the tile.
     * @param type type of the tile
     * @param pos position on the map
     * @param objIndex id of the stored {@link GameObject}
     * @see ObjectInfo
     */
    public TileWithObject(TileType type, Point pos, int objIndex) {
        super(type);
        this.pos = pos;
        object = ObjectInfo.getObject(objIndex);

        assert object != null;
        object.setTile(this);
    }

    @Override
    public void draw(GraphicsContext gc, int i, int j) {
        super.draw(gc, i, j);
        gc.drawImage(Textures.getTexture(object.getSprite()), i, j, 1.0, 1.0);
    }

    public Point getPos() {
        return pos;
    }

    public void setObject(GameObject object) {
        this.object = object;
    }

    public GameObject getObject() {
        return object;
    }

}

package dslite.world.entity.object;

import dslite.world.entity.Sprite;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

/**
 * Describes the visible objects on the map.
 * All of the game objects are stored within a {@link TileWithObject}.
 */
public abstract class GameObject {

    protected Sprite sprite;
    protected ObjectType type;
    protected TileWithObject tile;
    protected byte quantity;

    /**
     * Constructs a game object.
     *
     * @param type type of the object
     */
    protected GameObject(ObjectType type) {
        this.type = type;
        sprite = type.getSprite();
        quantity = 1;
    }

    /**
     * Constructs a game object and also sets the owner tile.
     *
     * @param type type of the object
     * @param tile reference of the tile
     */
    protected GameObject(ObjectType type, TileWithObject tile) {
        this(type);
        this.tile = tile;
    }

    /**
     * Constructs a game object by id.
     *
     * @param index the object id
     * @see ObjectType
     */
    protected GameObject(int index) {
        this(ObjectType.values()[index-1]);
    }

    public abstract void interact(Player player);

    public void setType(ObjectType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(byte quantity) {
        this.quantity = quantity;
    }

    public ObjectType getType() {
        return type;
    }

    public TileWithObject getTile() {
        return tile;
    }

    public void setTile(TileWithObject tile) {
        this.tile = tile;
    }

    protected void setSprite(int spriteIndex) {
        this.sprite = new Sprite(spriteIndex);
    }

    public Sprite getSprite() {
        return sprite;
    }

}

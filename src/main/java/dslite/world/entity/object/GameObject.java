package dslite.world.entity.object;

import dslite.world.entity.Sprite;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

/**
 * A játékban előforduló Tile-okon megjelenő objektumokat írja le,
 * Ezekkel különböző interakciókat tud végezni a játékos.
 */
public abstract class GameObject {

    protected Sprite sprite;
    protected ObjectType type;
    protected TileWithObject tile;
    protected byte quantity;

    /**
     * A GameObject konstruktora.
     *
     * @param type A hozzá tartozó ObjectType
     * @see ObjectType
     */
    protected GameObject(ObjectType type) {
        this.type = type;
        sprite = type.getSprite();
        quantity = 1;
    }

    /**
     * Konstruktor, ami az őt tartalmazó mezőt is beállítja
     *
     * @param type A hozzá tartozó ObjectType
     * @param tile A hozzá tartozó mező referenciája
     */
    protected GameObject(ObjectType type, TileWithObject tile) {
        this(type);
        this.tile = tile;
    }

    /**
     * Konstruktor, ami index alapján készíti el az objektumot.
     *
     * @param index A GameObjecthez tartozó index
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

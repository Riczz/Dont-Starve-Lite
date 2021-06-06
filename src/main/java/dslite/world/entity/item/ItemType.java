package dslite.world.entity.item;

import dslite.world.entity.Sprite;
import dslite.world.entity.Textures;
import dslite.world.entity.object.GameObject;
import javafx.scene.image.Image;

/**
 * This enum describes the possible {@linkplain Item}s in the player's inventory.
 */
public enum ItemType {

    //CRAFTING
    CUTGRASS((byte) 24, "Cut grass", (byte) 17, true),
    FLINT((byte) 25, "Flint", (byte) 15, true),
    GOLD((byte) 26, "Gold", (byte) 13, true),
    LOG((byte) 27, "Log", (byte) 18, true),
    PETAL((byte) 28, "Petal", (byte) 19, true),
    ROCKS((byte) 29, "Rocks", (byte) 12, true),
    TWIGS((byte) 30, "Twigs", (byte) 20, true),

    //FOOD
    BERRIES((byte) 31, "Berries", (byte) 21, true),
    CARROT((byte) 33, "Carrot", (byte) 22, true),
    BERRIES_COOKED((byte) 32, "Cooked Berries", (byte) 26, true),
    CARROT_COOKED((byte) 34, "Cooked Carrot", (byte) 27, true),

    //SURVIVAL
    CAMPFIRE((byte) 38, "Campfire", (byte) 16, false),
    GARLAND((byte) 39, "Garland", (byte) 23, false),

    //TOOLS
    AXE((byte) 40, "Axe", (byte) 24, false),
    PICKAXE((byte) 41, "Pickaxe", (byte) 25, false);

    private final Sprite sprite;
    private final String name;
    private final byte objIndex;
    private final boolean isPlaceable;
    private final boolean isStackable;

    /**
     * Constructor for an item.
     *
     * @param spriteIndex index for the item's {@link Sprite}.
     * @param name        name of the item
     * @param objIndex    {@link GameObject} id for the dropped equivalent
     * @param isStackable can the item be stacked in the inventory
     * @see dslite.world.entity.object.ObjectInfo
     */
    ItemType(byte spriteIndex, String name, byte objIndex, boolean isStackable) {
        sprite = new Sprite(spriteIndex);
        this.name = name;
        this.objIndex = objIndex;
        this.isStackable = isStackable;
        this.isPlaceable = objIndex != -1;
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public static Image getImage(Sprite sprite) {
        return Textures.getTexture(sprite);
    }

    public byte getObjectIndex() {
        return objIndex;
    }

    public boolean isPlaceable() {
        return isPlaceable;
    }

    public boolean isStackable() {
        return isStackable;
    }
}

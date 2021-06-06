package dslite.world.player;

import dslite.controllers.GameController;
import dslite.gui.crafting.CraftingView;
import dslite.gui.inventory.InventoryComponent;
import dslite.world.GameState;
import dslite.world.Updatable;
import dslite.world.World;
import dslite.world.WorldMap;
import dslite.world.biomes.Point;
import dslite.world.entity.Sprite;
import dslite.world.entity.Textures;
import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemInfo;
import dslite.world.entity.item.ItemType;
import dslite.world.entity.item.food.Food;
import dslite.world.entity.object.ObjectInfo;
import dslite.world.player.inventory.Inventory;
import dslite.world.tiles.Tile;
import dslite.world.tiles.TileWithObject;
import javafx.scene.image.Image;

public final class Player implements Updatable {

    public static final Image IMG = Textures.getTexture(new Sprite(1));
    public static final float MAX_HEALTH = 100.0f;
    public static final float MAX_SANITY = 100.0f;
    public static final float MAX_HUNGER = 100.0f;

    private int posX;
    private int posY;
    private float health;
    private float sanity;
    private float hunger;
    private byte actionPoints;
    private boolean hasCampFireNearby;

    private Tile[][] tilemap;
    private GameController controller;
    private Inventory inventory;
    private WorldMap map;
    private World world;

    public Player() {
        health = MAX_HEALTH;
        sanity = MAX_SANITY;
        hunger = MAX_HUNGER;
        actionPoints = World.AP_DAY;
    }

    public Player(World world) {
        this();
        this.world = world;
        this.map = world.getMap();
        this.tilemap = map.getTilemap();
        controller = world.getController();
        controller.setInv(new InventoryComponent(this));
        inventory = new Inventory(this);
        setPos(map.getSpawnPoint());
    }

    @Override
    public void update() {

        GameController.getGrid().drawPlayer(this);

        hasCampFireNearby = false;

        if (hunger <= 0.0f || sanity <= 0.0f || health <= 0.0f) {
            controller.endGame();
        }

        inventory.update();
        controller.getInfoComponent().update();
    }

    /**
     * Tries to interact with the {@link Tile} at the player's current position.
     *
     * @see TileWithObject
     */
    public void interact() {
        if (getTile() instanceof TileWithObject) {
            ((TileWithObject) getTile()).getObject().interact(this);
        }
    }

    /**
     * Drops the currently selected {@link Item} in the inventory.
     * Only works if the item is placeable.
     *
     * @see ObjectInfo
     */
    public void place() {
        if (getTile() instanceof TileWithObject) return;

        Item item = getEquippedItem();
        if (item == null || !item.getType().isPlaceable()) return;

        TileWithObject obj =
                new TileWithObject(getTile().getType(), getPos(),
                        item.getType().getObjectIndex());


        obj.getObject().setQuantity((byte) getEquippedItemCount());
        removeEquippedItem();
        setTile(obj);
    }

    /**
     * Moves the player on the map.
     *
     * @param x no. of steps horizontally
     * @param y no. of steps vertically
     */
    public void move(byte x, byte y) {
        if (!tilemap[posX + x][posY + y].getType().isSolid()) {
            posX += x;
            posY += y;
            addActionPoints((byte) -1);
        }
    }

    public void eat() {
        Item item = getEquippedItem();
        if (item == null) return;

        if (item instanceof Food) {
            ((Food) item).eat();
            addActionPoints((byte) -1);
            getInventory().removeItemByType(item.getType(), 1);
        }
    }

    /**
     * Tries to craft the specified {@link ItemType}.
     * If the operation was successful, AP gets reduced by {@code 1}.
     *
     * @param item the item to be crafted
     * @return {@code true} if the crafting was successful, {@code false} if not
     * @see Inventory
     */
    public boolean craft(ItemType item) {
        if (inventory.tryToCraft(ItemInfo.getItem(item), true)) {
            addActionPoints((byte) -1);
            return true;
        }
        return false;
    }

    /**
     * Checks if the player is able to craft the specified Item based on the state of the inventory.<br/>
     * This function is important for the {@link CraftingView}.
     *
     * @param item the item to check
     * @return does the player has all the required items for crafting
     * @see dslite.gui.crafting.CraftingView
     * @see dslite.gui.crafting.ItemList
     */
    public boolean canCraft(ItemType item) {
        return inventory.tryToCraft(ItemInfo.getItem(item), false);
    }

    /**
     * Checks if the player has the specified {@link Item} inside their inventory.
     *
     * @param item the searched item
     * @return {@code true} if present, {@code false} if not
     */
    public boolean hasItem(ItemType item) {
        return getInventory().getItemCountFor(item) >= 1;
    }

    /**
     * Gets called when the player spends a certain amount of AP.<br/>
     * Only accepts negative values.
     *
     * @param actionPoints the amount of AP spent
     */
    public void addActionPoints(byte actionPoints) {
        if (actionPoints >= 0) return;

        this.actionPoints += actionPoints;
        addHunger(0.4f * actionPoints);

        if (getWorld().getGameState() == GameState.DAY) {
            if (hasItem(ItemType.GARLAND)) {
                addSanity(-0.05f);
            }
        } else {
            if (hasItem(ItemType.GARLAND)) {
                addSanity(0.3f * actionPoints);
            } else {
                addSanity(0.4f * actionPoints);
            }

            if (!hasCampFireNearby) {
                addHealth((byte) -5);
                addSanity((byte) -7);
            }
        }

    }

    private void setPos(Point p) {
        this.posX = p.getX();
        this.posY = p.getY();
    }

    public void addHealth(float val) {
        if (val < 0) {
            health = Math.max(health + val, 0.0f);
        } else {
            health = Math.min(health + val, MAX_HEALTH);
        }
    }

    public void addSanity(float val) {
        if (val < 0) {
            sanity = Math.max(sanity + val, 0.0f);
        } else {
            sanity = Math.min(sanity + val, MAX_SANITY);
        }
    }

    public void addHunger(float val) {
        if (val < 0) {
            hunger = Math.max(hunger + val, 0.0f);
        } else {
            hunger = Math.min(hunger + val, MAX_HUNGER);
        }
    }

    public Item getEquippedItem() {
        return getInventory().getSelectedSlot().getStoredItem();
    }

    public int getEquippedItemCount() {
        return getInventory().getSelectedSlot().getItemCount();
    }

    public void removeEquippedItem() {
        getInventory().removeSlot(getInventory().getSelectedSlot());
    }

    private void setTile(TileWithObject tile) {
        getTilemap()[getPosX()][getPosY()] = tile;
    }

    private Tile getTile() {
        return getTilemap()[getPosX()][getPosY()];
    }

    public World getWorld() {
        return world;
    }

    public WorldMap getMap() {
        return map;
    }

    public Tile[][] getTilemap() {
        return tilemap;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public GameController getController() {
        return controller;
    }

    public byte getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(byte actionPoints) {
        this.actionPoints = actionPoints;
    }

    public void setHasCampFireNearby(boolean hasCampFireNearby) {
        this.hasCampFireNearby = hasCampFireNearby;
    }

    public boolean hasCampFireNearby() {
        return hasCampFireNearby;
    }

    public float getHealth() {
        return health;
    }

    public float getSanity() {
        return sanity;
    }

    public float getHunger() {
        return hunger;
    }

    public Point getPos() {
        return new Point(posX, posY);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

}

package dslite.world.player;

import dslite.controllers.GameController;
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

/**
 * A játékost leíró osztály.
 */
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

    /**
     * Beállítja a játékos értékeit a maximumra.
     */
    public Player() {
        health = MAX_HEALTH;
        sanity = MAX_SANITY;
        hunger = MAX_HUNGER;
        actionPoints = World.AP_DAY;
    }

    /**
     * Beállítja a játékos értékeit, és hozzárendeli a játékoshoz a világot.
     * @param world A hozzárendelt Világ
     */
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

    /**
     * Játékos állapotát frissítő metódus.
     */
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
     * Interakció a játékos jelenlegi pozícióján lévő Tile-al.
     *
     * @see TileWithObject
     */
    public void interact() {
        if (getTile() instanceof TileWithObject) {
            ((TileWithObject) getTile()).getObject().interact(this);
        }
    }

    /**
     * A játékos kezében lévő Item eldobása.
     * Mindig a kézben tartott mennyiségnek megfelelő darabszám dobódik el.
     * Csak akkor működik, ha a kézben tartott Item eldobható, és a jelenlegi mező üres.
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
     * Elmozdulás a pályán.
     *
     * @param x Lépés száma vízszintes irányban
     * @param y Lépés száma függőleges irányban
     */
    public void move(byte x, byte y) {
        if (!tilemap[posX + x][posY + y].getType().isSolid()) {
            posX += x;
            posY += y;
            addActionPoints((byte) -1);
        }
    }

    /**
     * A játékos eszik metódusa.
     *
     * @see Food
     */
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
     * A paraméterben átadott Itemet megpróbálja lecraftolni az Inventory alapján.
     * Ha a craftolás sikeres, 1 cselekvéspont levonódik.
     *
     * @param item A craftolni kívánt Item.
     * @return igaz ha sikerült, hamis ha nem
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
     * Visszaadja, hogy a játékos képes-e lecraftolni a megadott Itemet az Inventory alapján.
     * A Craft nézetnél fontos, hogy tudjuk.
     *
     * @param item Az ellenőrizni kívánt Item
     * @return Le tudja-e craftolni
     * @see dslite.gui.crafting.CraftingView
     * @see dslite.gui.crafting.ItemList
     */
    public boolean canCraft(ItemType item) {
        return inventory.tryToCraft(ItemInfo.getItem(item), false);
    }

    /**
     * Megnézi, hogy a játékos Inventoryjában szerepel-e a megadott Item.
     *
     * @param item A keresett Item
     * @return Szerepel-e az inventoryban
     */
    public boolean hasItem(ItemType item) {
        return getInventory().getItemCountFor(item) >= 1;
    }

    /**
     * Cselekvéspontok hozzáadása.
     * Csak negatív értékeket fogad el.
     * Cselekvéspontonként az éhség 0.4-del csökken.
     *
     * @param actionPoints Elvett cselekvéspontok száma
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

    /**
     * Visszaadja a jelenleg kiválasztott Inventory slotban található Itemet.
     *
     * @return A kiválasztott Item
     */
    public Item getEquippedItem() {
        return getInventory().getSelectedSlot().getStoredItem();
    }

    /**
     * Visszaadja, hogy a jelenleg kiválasztott Itemből hány darab van a játékosnál.
     *
     * @return Az eltárolt darabszám
     */
    public int getEquippedItemCount() {
        return getInventory().getSelectedSlot().getItemCount();
    }

    /**
     * Törli a jelenleg kiválasztott slotban található Itemet.
     */
    public void removeEquippedItem() {
        getInventory().removeSlot(getInventory().getSelectedSlot());
    }

    /**
     * Élet hozzáadása.
     *
     * @param val hozzáadni kívánt mennyiség
     */
    public void addHealth(float val) {
        if (val < 0) {
            health = Math.max(health + val, 0.0f);
        } else {
            health = Math.min(health + val, MAX_HEALTH);
        }
    }

    /**
     * Agy hozzáadása.
     *
     * @param val hozzáadni kívánt mennyiség
     */
    public void addSanity(float val) {
        if (val < 0) {
            sanity = Math.max(sanity + val, 0.0f);
        } else {
            sanity = Math.min(sanity + val, MAX_SANITY);
        }
    }

    /**
     * Éhség hozzáadása.
     *
     * @param val hozzáadni kívánt mennyiség
     */
    public void addHunger(float val) {
        if (val < 0) {
            hunger = Math.max(hunger + val, 0.0f);
        } else {
            hunger = Math.min(hunger + val, MAX_HUNGER);
        }
    }

    /**
     * Játékos pozíciójának beállítása
     *
     * @param p A pozíció
     */
    private void setPos(Point p) {
        this.posX = p.getX();
        this.posY = p.getY();
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

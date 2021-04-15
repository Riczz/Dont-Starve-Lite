package dslite.gui;

import dslite.world.Updatable;
import dslite.world.player.Player;

/**
 * A játékban használt kamera, a játékos pozíciója alapján
 * határozza meg az eltolás értékét, és folyamatosan követi a játékost.
 */
public final class Camera implements Updatable {

    private final int width;
    private final int height;
    private int xOffset;
    private int yOffset;
    private Player player;

    private static final int COLS = GameScreen.COL_COUNT;
    private static final int ROWS = GameScreen.ROW_COUNT;

    /**
     * Konstruktor, ami beállítja a kamera szélességét és magasságát.
     *
     * @param width  Szélesség
     * @param height Magasság
     */
    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Konstruktor, ami beállítja a kamera szélességét, magasságát, és a hozzá tartozó játékost.
     *
     * @param width  Szélesség
     * @param height Magasság
     * @param player A beállított játékos
     */
    public Camera(int width, int height, Player player) {
        this(width, height);
        setPlayer(player);
    }

    /**
     * A kamerát frissítő metódus.
     * Leellenőrzi a játékostól való távolságot, ha kívül esne a
     * pálya határain, akkor beállítja maximum/minimum értékre.
     * A Gamescreen draw() metódusa hívja meg.
     *
     * @see GameScreen
     * @see Player
     */
    @Override
    public void update() {
        int x = player.getPosX();
        int y = player.getPosY();

        xOffset = x - COLS / 2;
        if (xOffset > 0) {
            xOffset = Math.min(x - COLS / 2, width - 1);
        }

        yOffset = y - ROWS / 2;
        if (yOffset > 0) {
            yOffset = Math.min(y - ROWS / 2, height - 1);
        }
    }

    /**
     * Beállítja a kamera által követett játékost.
     *
     * @param player A beállított játékos
     */
    public void setPlayer(Player player) {
        this.player = player;
        update();
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }
}

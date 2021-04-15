package dslite.gui;

import dslite.world.GameState;
import dslite.world.Updatable;
import dslite.world.World;
import dslite.world.WorldMap;
import dslite.world.biomes.Point;
import dslite.world.player.Player;
import dslite.world.tiles.Tile;
import dslite.world.tiles.TileType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

/**
 * A játékot megjelenítő képernyő.
 * Singleton osztály.
 */
public final class GameScreen extends Canvas implements Updatable {

    public static final int COL_COUNT = 25;
    public static final int ROW_COUNT = 25;
    private static final double PREF_WIDTH = 700.0;
    private static final double PREF_HEIGHT = 700.0;
    private static final boolean DRAW_GRID = true;
    private static final double GRID_THICKNESS = 0.01;
    private static double cellWidth = Math.round(PREF_WIDTH / COL_COUNT);
    private static double cellHeight = Math.round(PREF_HEIGHT / ROW_COUNT);
    private final GraphicsContext gc = getGraphicsContext2D();

    private Camera cam;
    private Player player;
    private World world;
    private WorldMap map;
    private Tile[][] tilemap;

    private static GameScreen instance = null;

    /**
     * A játékban használt grid, ez jeleníti meg a Tile-okat.
     *
     * @see dslite.controllers.GameController
     */
    private GameScreen() {
        super(PREF_WIDTH, PREF_HEIGHT);
        prefWidth(PREF_WIDTH);
        prefHeight(PREF_HEIGHT);

        //Ez egy transzformáció, ami az 1x1-es négyzeteket felskálázza a képernyő méreteihez
        //És végül így lesz kirajzolva a képernyő.
        Affine affine = new Affine();
        affine.appendScale(Math.round(getWidth() / COL_COUNT), Math.round(getHeight() / ROW_COUNT));
        gc.setTransform(affine);

        gc.setLineWidth(GRID_THICKNESS);
        gc.setStroke(Color.BLACK);
        setOnMouseClicked(mouseEvent -> requestFocus());
        instance = this;
    }

    /**
     * Frissíti a kamerát, majd kirajzolja a képernyőt.
     */
    @Override
    public void update() {
        cam.update();
        draw();
    }

    /**
     * Képernyő kirajzolásáért felelős metódus.
     */
    public void draw() {

        //Képernyő kitöltése vízzel
        gc.setFill(TileType.WATER.getColor());
        gc.fillRect(0, 0, getWidth(), getHeight());

        //Kamera pozíció lekérdezése
        int xOffset = cam.getxOffset();
        int yOffset = cam.getyOffset();

        //Képernyő kirajzolása cellánként
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                int posX = xOffset + i;
                int posY = yOffset + j;
                if (posX > 0 && posX < map.getWidth() && posY > 0 && posY < map.getHeight()) {
                    tilemap[posX][posY].draw(gc, i, j);
                }
            }
        }

        //Rács kirajzolása, ha be van állítva
        if (DRAW_GRID) drawGrid();

        //Éjszaka kép besötétíése
        if (world.getGameState() == GameState.NIGHT) drawNightOverlay();
    }

    /**
     * A játékos kirajzolása.
     */
    public void drawPlayer(Player player) {
        gc.drawImage(Player.IMG, player.getPosX() - cam.getxOffset(),
                player.getPosY() - cam.getyOffset(), 1.0, 1.0);
    }

    /**
     * Rács kirajzolása
     */
    private void drawGrid() {
        for (int i = 0; i < COL_COUNT; i++) {
            gc.strokeLine(i, 0.0, i, ROW_COUNT);
        }

        for (int i = 0; i < ROW_COUNT; i++) {
            gc.strokeLine(0.0, i, COL_COUNT, i);
        }
    }

    /**
     * Éjszakai képernyő
     */
    private void drawNightOverlay() {
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha(0.5);
        gc.fillRect(0, 0, getWidth(), getHeight());
        gc.setGlobalAlpha(1.0);
    }

    /**
     * Adott pozíción lévő Tile kirajzolása, ha benne van a kamera látóterében.
     * Ha be van állítva a rács, akkor azt is rajzol köré.
     * A Tábortűz használja.
     *
     * @param pos A mező pozíciója (tilemap koordináták)
     * @see dslite.world.entity.object.CampfireObj
     */
    public void drawTileAtPos(Point pos) {
        int posX = pos.getX() - cam.getxOffset();
        int posY = pos.getY() - cam.getyOffset();

        if (posX >= 0 && posX < COL_COUNT && posY >= 0 && posY < ROW_COUNT) {
            tilemap[pos.getX()][pos.getY()].draw(gc, posX, posY);

            if (DRAW_GRID) {
                gc.strokeRect(posX, posY, 1.0, 1.0);
            }
        }
    }

    /**
     * Világ hozzáadása.
     *
     * @param world A hozzáadott világ objektum
     */
    public void setWorld(World world) {
        this.world = world;
        this.map = world.getMap();
        this.player = world.getPlayer();
        this.tilemap = map.getTilemap();
        cam = new Camera(map.getWidth(), map.getHeight(), player);
        draw();
        drawPlayer(player);
    }

    public static GameScreen getInstance() {
        return instance == null ? new GameScreen() : instance;
    }

}

package dslite.world.player;

import dslite.gui.GameScreen;
import dslite.world.Updatable;
import dslite.world.player.Player;

/**
 * Camera that follows the player.
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
     * Initializes the camera with specified width and height.
     *
     * @param width  camera width
     * @param height camera height
     */
    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Initializes the camera with specified width and height and
     * also sets the {@link Player}.
     *
     * @param width  camera width
     * @param height camera height
     * @param player the player to follow
     */
    public Camera(int width, int height, Player player) {
        this(width, height);
        setPlayer(player);
    }

    /**
     * Updates the camera view.
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

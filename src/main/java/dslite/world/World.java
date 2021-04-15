package dslite.world;

import dslite.controllers.GameController;
import dslite.controllers.MenuController;
import dslite.world.player.Player;

/**
 * A világot, és a hozzá tartozó adatokat összefogó osztály.
 */
public final class World implements Updatable {

    private final Player player;
    private final WorldMap map;
    private final GameController controller;

    private int dayCount = 0;
    private GameState gameState;

    public static final byte AP_DAY = 85;                                               //Cselekvéspontok nappal
    public static final byte AP_NIGHT = 30;                                             //Cselekvéspontok éjszaka
    public static final byte DAY_LENGTH = AP_DAY + AP_NIGHT;                            //Nap hossza

    /**
     * A világ konstruktora.
     * @param controller A vezérlést elvégző kontroller osztály
     */
    public World(GameController controller) {
        this.controller = controller;
        gameState = GameState.DAY;
        map = new WorldMap(MenuController.getSizeX(),MenuController.getSizeY());
        player = new Player(this);
    }

    /**
     * Világ frissítése.
     * Ha a játékosnak elfogytak a cselekvéspontjai, átávltja a napszakot.
     */
    @Override
    public void update() {
        if (player.getActionPoints() <= 0) {
            changeGameState();
        }
    }

    /**
     * Napszak átváltása
     */
    private void changeGameState() {
        if (gameState == GameState.DAY) {
            player.setActionPoints(AP_NIGHT);
            gameState = GameState.NIGHT;
        } else {
            ++dayCount;
            player.setActionPoints(AP_DAY);
            gameState = GameState.DAY;
        }
    }

    public int getDayCount() {
        return dayCount;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameController getController() {
        return controller;
    }

    public WorldMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

}

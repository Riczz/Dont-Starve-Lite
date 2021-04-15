package dslite.world.entity.object;

import dslite.controllers.GameController;
import dslite.world.Drawable;
import dslite.world.GameState;
import dslite.world.World;
import dslite.world.WorldMap;
import dslite.world.biomes.Point;
import dslite.world.Updatable;
import dslite.world.entity.item.food.Food;
import dslite.world.player.Player;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * A Tábortűz GameObject-et leíró osztály.
 */
public final class CampfireObj extends GameObject implements Drawable, Updatable {

    private final Point pos;                                                //A tábortűz pozíciója
    private final int placedAtDayNum;                                       //Hanyadik napon lett lerakva
    private static final int LIGHT_DIST = 5;                                //Milyen távolságban világítja meg a teret

    //Referenciák
    private final World world = GameController.getWorld();
    private final Player player = world.getPlayer();

    //Megvilágított mezők
    private final List<Point> litTiles;

    /**
     * A tábortűz konstruktora.
     * Beállítja a pozícióját, lekérdezi a jelenlegi nap számát.
     * Kiszámítja, hogy melyik mezőket világítja meg, ezeket eltárolja egy listában.
     */
    public CampfireObj() {
        super(ObjectType.CAMPFIRE);
        pos = player.getPos();
        placedAtDayNum = world.getDayCount();

        //Hozzáadás a frissíthető GameObjectek listához
        WorldMap map = world.getMap();
        map.addToUpdatable(this);

        //Játékos cselekvéspont levonása
        Player player = GameController.getPlayer();
        player.addActionPoints((byte) -2);


        //A megvilágított mezők kiszámítása
        litTiles = new ArrayList<>();
        for (int i = pos.getX() - LIGHT_DIST; i <= pos.getX() + LIGHT_DIST; i++) {
            for (int j = pos.getY() - LIGHT_DIST; j <= pos.getY() + LIGHT_DIST; j++) {
                if (i > 0 && i < map.getWidth() && j > 0 && j < map.getHeight()
                        && Point.mDist(pos, i, j) <= LIGHT_DIST) {
                    litTiles.add(new Point(i,j));
                }
            }
        }

    }

    /**
     * Folyamatosan ellenőrzi, hogy eltelt-e már a nap, ha igen, törli magát.
     * Este a játékostól való távolságot is figyeli.
     */
    @Override
    public void update() {
        if (world.getDayCount() > placedAtDayNum) {
            Platform.runLater(() -> {
                GameController.getMap().setTileAtPos(pos, getTile().getType());
                GameController.getMap().removeFromUpdatable(this);
            });
        }

        if (world.getGameState() == GameState.NIGHT) {

            //Kirajzolja magát
            draw(GameController.getGrid().getGraphicsContext2D(), pos.getX(), pos.getY());

            //Ha a játékosnak este nincs beállítva, hogy van a közelében tábortűz,
            //Mindegyik egyesével ellenőrzi a távolságát a játékostól, ha bármelyik érzékeli, hogy
            //A játékos a közelében tartózkodik, átállítja a tulajdonságot, így a többinek nem kell tovább ellenőrizni.
            if (!player.hasCampFireNearby()) {
                if (Point.mDist(pos, player.getPosX(), player.getPosY()) <= LIGHT_DIST) {
                    player.setHasCampFireNearby(true);
                }
            }
        }
    }

    /**
     * Kirajzolja az összes megvilágított mezőt.
     */
    @Override
    public void draw(GraphicsContext gc, int i, int j) {
        for (Point p : litTiles) {
            GameController.getGrid().drawTileAtPos(p);
        }
    }

    @Override
    public void interact(Player player) {
        if (player.getEquippedItem() instanceof Food) {
            ((Food) player.getEquippedItem()).cook();
        }
    }
}

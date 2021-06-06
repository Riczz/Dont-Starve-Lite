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

public final class CampfireObj extends GameObject implements Drawable, Updatable {

    private final Point pos;
    private final int placedAtDayNum;
    private static final int LIGHT_DIST = 5;

    private final World world = GameController.getWorld();
    private final Player player = world.getPlayer();

    private final List<Point> litTiles;

    public CampfireObj() {
        super(ObjectType.CAMPFIRE);
        pos = player.getPos();
        placedAtDayNum = world.getDayCount();

        WorldMap map = world.getMap();
        map.addToUpdatable(this);

        Player player = GameController.getPlayer();
        player.addActionPoints((byte) -2);


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
     * If the current day has passed, the campfire gets deleted.
     * It also checks the distance from the player during nighttime.
     */
    @Override
    public void update() {
        if (world.getDayCount() > placedAtDayNum) {
            //TODO: find a fix for ConcurrentModificationException
            Platform.runLater(() -> {
                GameController.getMap().setTileAtPos(pos, getTile().getType());
                GameController.getMap().removeFromUpdatable(this);
            });
        }

        if (world.getGameState() == GameState.NIGHT) {

            //Draws itself
            draw(GameController.getGrid().getGraphicsContext2D(), pos.getX(), pos.getY());

            //Every campfire checks if the player is inside their range
            if (!player.hasCampFireNearby()) {
                if (Point.mDist(pos, player.getPosX(), player.getPosY()) <= LIGHT_DIST) {
                    player.setHasCampFireNearby(true);
                }
            }
        }
    }

    /**
     * Draws the lit tiles.
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

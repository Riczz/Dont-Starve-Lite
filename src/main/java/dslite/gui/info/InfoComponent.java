package dslite.gui.info;

import dslite.controllers.GameController;
import dslite.world.player.Player;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * GUI class for displaying information about the {@link Player}.
 *
 * @see StatBox
 * @see StatBoxSimple
 */
public final class InfoComponent extends VBox {

    private final StatBox health;
    private final StatBox hunger;
    private final StatBox sanity;
    private final StatBoxSimple ap;
    private final Player player;

    public InfoComponent() {
        super(20.0);
        player = GameController.getPlayer();

        setPrefWidth(300.0);
        setPrefHeight(GameController.getGrid().getHeight());
        setMinHeight(200.0);
        setAlignment(Pos.TOP_LEFT);

        health = new StatBox("Health: ", Player.MAX_HEALTH);
        hunger = new StatBox("Hunger: ", Player.MAX_HUNGER);
        sanity = new StatBox("Sanity: ", Player.MAX_SANITY);
        ap = new StatBoxSimple("AP: ");

        update();
        getChildren().addAll(health, hunger, sanity, ap);

    }

    /**
     * Refreshes the displayed values based on the player's stats.
     */
    public void update() {
        setHealth(player.getHealth());
        setHunger(player.getHunger());
        setSanity(player.getSanity());
        setActionPoints(player.getActionPoints());
    }

    private void setHealth(float val) {
        health.setValue(val);
    }

    private void setHunger(float val) {
        hunger.setValue(val);
    }

    private void setSanity(float val) {
        sanity.setValue(val);
    }

    private void setActionPoints(float val) {
        ap.setValue(val);
    }

}

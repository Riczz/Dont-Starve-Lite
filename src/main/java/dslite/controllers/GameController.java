package dslite.controllers;

import dslite.gui.GameScreen;
import dslite.gui.crafting.CraftingView;
import dslite.gui.info.InfoComponent;
import dslite.gui.inventory.InventoryComponent;
import dslite.world.World;
import dslite.world.WorldMap;
import dslite.world.player.Player;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.util.EventListener;

/**
 * A játékot irányító kontroller osztály.
 * Tartalmazza a világ, játékos, és a térkép referenciáit,
 * és a nézethez kapcsolódó osztályokét is.
 * Összeköti a játékot a nézettel.
 *
 * @see World
 * @see WorldMap
 * @see Player
 *
 * @see GameScreen
 * @see InventoryComponent
 * @see CraftingView
 */
public final class GameController implements EventListener {

    @FXML
    private VBox mainPane;

    private static World world;
    private static WorldMap map;
    private static Player player;

    private static GameScreen grid;
    private InventoryComponent inv;
    private CraftingView craftingView;
    private InfoComponent info;

    private EventHandler<KeyEvent> keyHandler;

    /**
     * Az ablak megnyitásakor automatikusan lefutó metódus.
     * Feltölti az ablakot a nézethez tartozó osztályokkal.
     * Létrehozza a világot, a játékost, és átadja a referenciákat az azokhoz tartozó nézet elemhez.
     */
    @FXML
    public void initialize() {

        world = new World(this);
        map = world.getMap();
        player = world.getPlayer();

        grid = GameScreen.getInstance();
        grid.setWorld(world);

        craftingView = new CraftingView();
        info = new InfoComponent();

        HBox hbox = new HBox(25.0);
        hbox.getChildren().addAll(craftingView, grid, info);
        hbox.setAlignment(Pos.CENTER);

        mainPane.getChildren().addAll(hbox, inv);
        setKeyListener();
        enableView();
    }

    /**
     * Beállítja az eseménykezelőt a billentyűzet gombjaihoz.
     *
     * @see Player
     * @see KeyEvent
     */
    private void setKeyListener() {
        keyHandler = keyEvent -> {
            KeyCode code = keyEvent.getCode();

            switch (code) {
                case DIGIT1:
                case DIGIT2:
                case DIGIT3:
                case DIGIT4:
                case DIGIT5:
                case DIGIT6:
                case DIGIT7:
                case DIGIT8:
                case DIGIT9: {
                    player.getInventory().setSelectedSlot(Integer.parseInt(code.getChar()) - 1);
                    break;
                }
                case W: {                                           //Elmozdulás felfelé
                    player.move((byte) 0, (byte) -1);
                    break;
                }
                case A: {                                           //Elmozdulás balra
                    player.move((byte) -1, (byte) 0);
                    break;
                }
                case S: {                                           //Elmozdulás lefelé
                    player.move((byte) 0, (byte) 1);
                    break;
                }
                case D: {                                           //Elmozdulás jobbra
                    player.move((byte) 1, (byte) 0);
                    break;
                }
                case T: {                                           //Várakozás
                    player.move((byte) 0, (byte) 0);
                    break;
                }
                case SPACE: {                                       //Interakció
                    player.interact();
                    break;
                }
                case E: {                                           //Evés
                    player.eat();
                    break;
                }
                case F: {                                           //Kézben tartott Item lerakása
                    player.place();
                    break;
                }
                case X: {                                           //Kézben tartott Item törlése
                    player.removeEquippedItem();
                    break;
                }
            }

            //Nézet és világ frissítése
            //Nem a legjobb megoldás így, de mind1
            world.update();
            grid.update();
            player.update();
            map.update();
        };
        mainPane.setOnKeyPressed(keyHandler);
    }

    /**
     * A játék végét lekezelő metódus.
     */
    public void endGame() {
        disableView();

        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Game over! You survived for a total of " + world.getDayCount() + " days.", ButtonType.CLOSE);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Game over");
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> mainPane.getScene().getWindow().hide());
    }

    /**
     * Letiltja a képernyőn található elemeket.
     */
    private void disableView() {
        grid.setDisable(true);
        inv.setDisable(true);
        craftingView.setDisable(true);
        info.setDisable(true);
        mainPane.getScene().removeEventHandler(KeyEvent.ANY, keyHandler);
    }

    /**
     * Engedélyezi a képernyőn található elemeket.
     */
    private void enableView() {
        grid.setDisable(false);
        grid.setFocusTraversable(true);
        inv.setDisable(false);
        craftingView.setDisable(false);
        info.setDisable(false);
        mainPane.setOnKeyPressed(keyHandler);
    }

    public static World getWorld() {
        return world;
    }

    public static WorldMap getMap() {
        return map;
    }

    public static Player getPlayer() {
        return player;
    }

    public InventoryComponent getInv() {
        return inv;
    }

    public void setInv(InventoryComponent inv) {
        this.inv = inv;
    }

    public  InfoComponent getInfoComponent() {
        return info;
    }

    public  CraftingView getCraftingView() {
        return craftingView;
    }

    public static GameScreen getGrid() {
        return grid;
    }


}

package dslite.gui.crafting;

import dslite.controllers.GameController;
import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A crafting menüt leíró osztály.
 * A fő képernyőn bal oldalt jelenik meg, különböző menüpontokban
 * tartalmazza a craftolható elemek listáját.
 *
 * @see ItemList
 */
public final class CraftingView extends VBox {

    /**
     * A listában jelenleg kiválasztott elem típusa.
     *
     * @see ItemList
     * @see ItemType
     */
    protected ItemType selectedType;

    private TabPane tabPane;
    private Button craftBtn;
    private ObservableList<ItemType> tools;
    private ObservableList<ItemType> survival;

    /**
     * A crafting menü konstruktora.
     * Létrehozza  a listát és a craft gombot.
     */
    public CraftingView() {
        super();
        initTabPane();
        initCraftBtn();

        craftBtn.setOnAction(actionEvent -> {
            Player player = GameController.getPlayer();
            player.craft(selectedType);
        });

        setPrefHeight(700.0);
        setFocusTraversable(false);
        getChildren().addAll(tabPane, new StackPane(craftBtn));
    }

    /**
     * A listákat tartalmazó TabPane beállításáért felelős metódus.
     * Feltölti elemekkel, megtiltja a bezárást, és a fókuszt.
     */
    private void initTabPane() {
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setPrefWidth(300.0);
        tabPane.setPrefHeight(GameController.getGrid().getHeight());
        tabPane.setFocusTraversable(false);
        tabPane.setSide(Side.LEFT);

        tools = FXCollections.observableArrayList(
                ItemType.AXE,
                ItemType.PICKAXE
        );
        Tab toolsTab = new Tab("Tools", new ItemList(tools, this));

        survival = FXCollections.observableArrayList(
                ItemType.GARLAND,
                ItemType.CAMPFIRE
        );
        Tab survivalTab = new Tab("Survival", new ItemList(survival, this));
        tabPane.getTabs().addAll(toolsTab, survivalTab);
    }

    /**
     * A Craft gombot beállító metódus.
     */
    private void initCraftBtn() {
        craftBtn = new Button("Craft");
        craftBtn.setTooltip(new Tooltip("Craft the selected item"));
        craftBtn.setPrefWidth(300.0);
        craftBtn.setFocusTraversable(false);
        craftBtn.setDisable(true);
        craftBtn.setOnAction(actionEvent -> setCraftBtn(GameController.getPlayer().canCraft(selectedType)));
    }

    /**
     * A nézet frissítése.
     * Leellenőrzi, hogy a játékos képes az Inventoryja alapján lecraftolni a
     * jelenleg kiválasztott itemet, és az alapján állítja be a Craft gomb állapotát.
     */
    public void update() {
        if (selectedType != null) {
            setCraftBtn(GameController.getPlayer().canCraft(selectedType));
        }
    }

    protected void setCraftBtn(boolean val) {
        craftBtn.setDisable(!val);
    }

}

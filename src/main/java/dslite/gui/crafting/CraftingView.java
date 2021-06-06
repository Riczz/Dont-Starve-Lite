package dslite.gui.crafting;

import dslite.controllers.GameController;
import dslite.world.entity.item.Item;
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
 * GUI class for the crafting menu.
 * It appears on the left side of the game screen
 * containing a list of the craftable items.
 *
 * @see ItemList
 */
public final class CraftingView extends VBox {

    /**
     * Type of the currently selected {@link Item}.
     *
     * @see ItemList
     * @see ItemType
     */
    protected ItemType selectedType;

    private TabPane tabPane;
    private Button craftBtn;

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

    private void initTabPane() {
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setPrefWidth(300.0);
        tabPane.setPrefHeight(GameController.getGrid().getHeight());
        tabPane.setFocusTraversable(false);
        tabPane.setSide(Side.LEFT);

        ObservableList<ItemType> tools = FXCollections.observableArrayList(
                ItemType.AXE,
                ItemType.PICKAXE
        );
        Tab toolsTab = new Tab("Tools", new ItemList(tools, this));

        ObservableList<ItemType> survival = FXCollections.observableArrayList(
                ItemType.GARLAND,
                ItemType.CAMPFIRE
        );
        Tab survivalTab = new Tab("Survival", new ItemList(survival, this));
        tabPane.getTabs().addAll(toolsTab, survivalTab);
    }

    private void initCraftBtn() {
        craftBtn = new Button("Craft");
        craftBtn.setTooltip(new Tooltip("Craft the selected item"));
        craftBtn.setPrefWidth(300.0);
        craftBtn.setFocusTraversable(false);
        craftBtn.setDisable(true);
        craftBtn.setOnAction(actionEvent -> setCraftBtn(GameController.getPlayer().canCraft(selectedType)));
    }

    /**
     * Checks if the player is able to craft the currently selected item and
     * sets the state of the craft button according to the result.
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

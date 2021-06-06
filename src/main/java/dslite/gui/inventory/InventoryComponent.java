package dslite.gui.inventory;

import dslite.controllers.GameController;
import dslite.gui.crafting.CraftingView;
import dslite.world.player.Player;
import dslite.world.player.inventory.Inventory;
import dslite.world.player.inventory.Slot;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * GUI class for displaying the {@link Inventory}.
 *
 * @see Player
 * @see Slot
 */
public final class InventoryComponent extends StackPane {

    private InventoryItem[] items;
    private final Player player;
    private final HBox hbox;
    private final GameController controller;

    public InventoryComponent(Player player) {
        super();
        setHeight(100.0);
        setMinHeight(100.0);

        this.player = player;
        controller = player.getWorld().getController();

        hbox = new HBox(10.0);
        hbox.setAlignment(Pos.CENTER);

        getChildren().add(hbox);
        initSlots();
    }

    /**
     * Updates the contents of the inventory.<br/>
     * Gets called whenever the state of the inventory changes.
     * The currently selected item will be displayed with red color.<br/>
     * Also updates the {@link CraftingView}.
     *
     * @see Inventory
     */
    public void update() {
        Inventory inv = player.getInventory();

        for (int i = 0; i < Inventory.INV_SIZE; i++) {
            Slot currentSlot = inv.getSlots()[i];

            items[i].setItem(currentSlot);

            if (inv.getSelectedSlot() == currentSlot) {
                items[i].getItemCount().setTextFill(Color.RED);
            } else {
                items[i].getItemCount().setTextFill(Color.WHITE);
            }
        }

        controller.getCraftingView().update();
    }

    private void initSlots() {
        items = new InventoryItem[Inventory.INV_SIZE];
        for (int i = 0; i < Inventory.INV_SIZE; i++) {
            hbox.getChildren().add(items[i] = new InventoryItem());
        }
    }

}

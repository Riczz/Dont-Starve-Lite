package dslite.gui.inventory;

import dslite.controllers.GameController;
import dslite.world.player.Player;
import dslite.world.player.inventory.Inventory;
import dslite.world.player.inventory.Slot;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * A játékos Inventoryját megjelenítő osztály.
 *
 * @see Player
 * @see Inventory
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
     * A nézet frissítésére szolgáló metódus.
     * Az Inventoryból hívódik meg, ha változik a tartalma.
     * A jelenleg kiválasztott Slotot piros színnel jeleníti meg.
     * Frissíti a Craft nézetet is.
     *
     * @see Inventory
     * @see dslite.gui.crafting.CraftingView
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

        //Craft lista frissítése
        controller.getCraftingView().update();
    }

    /**
     * A HBox-ban megjelenő Slotokat inicializáló metódus.
     *
     * @see InventoryItem
     */
    private void initSlots() {
        items = new InventoryItem[Inventory.INV_SIZE];
        for (int i = 0; i < Inventory.INV_SIZE; i++) {
            hbox.getChildren().add(items[i] = new InventoryItem());
        }
    }

}

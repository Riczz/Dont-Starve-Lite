package dslite.gui.crafting;

import dslite.world.entity.item.ItemType;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

/**
 * A crafting menüben megjelenő listákat megvalósító osztály.
 * Egyedi CellFactory-t állít be.
 *
 * @see javafx.scene.control.Cell
 * @see javafx.scene.control.ListCell
 * @see ListElement
 */
public final class ItemList extends ListView<ItemType> {

    protected ItemList(ObservableList<ItemType> items, CraftingView cw) {
        super();
        setCellFactory(listElement -> new ListElement());
        setFocusTraversable(false);
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
            cw.selectedType = newVal;
            cw.update();
        });
        getItems().addAll(items);
    }

}

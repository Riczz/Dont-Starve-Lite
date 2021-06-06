package dslite.gui.crafting;

import dslite.world.entity.item.ItemType;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

/**
 * The list inside the {@link CraftingView}.
 *
 * @see ListElement
 * @see javafx.scene.control.Cell
 * @see javafx.scene.control.ListCell
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

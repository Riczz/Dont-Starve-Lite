package dslite.gui.crafting;

import dslite.world.entity.item.ItemType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * A craft menüben megjelenő lista egy eleme.
 */
public final class ListElement extends ListCell<ItemType> {

    private final HBox container = new HBox(25.0d);
    private final ImageView icon = new ImageView();
    private final Label label = new Label();

    /**
     * A listaelem konstruktora.
     */
    public ListElement() {
        super();
        setMaxHeight(64.0);
        setPrefWidth(200.0);
        icon.prefWidth(64.0);
        icon.prefHeight(64.0);
        icon.maxWidth(64.0);
        icon.maxHeight(64.0);
        setFocusTraversable(false);

        StackPane pane = new StackPane();
        pane.getChildren().add(label);
        label.setAlignment(Pos.CENTER);

        container.getChildren().addAll(icon, pane);
    }

    /**
     * Új listaelem hozzáadásakor beállítja képnek a típushoz tartozó ikont,
     * és kiírja mellette a nevét.
     * @param type A listaelem által tárolt ItemType
     */
    @Override
    protected void updateItem(ItemType type, boolean empty) {
        super.updateItem(type, empty);
        setText(null);
        setGraphic(null);

        if (type != null && !empty) {
            icon.setImage(ItemType.getImage(type.getSprite()));
            label.setText(type.getName());
            setGraphic(container);
        }
    }
}

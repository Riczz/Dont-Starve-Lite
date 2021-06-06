package dslite.gui.inventory;

import dslite.world.entity.Textures;
import dslite.world.entity.item.ItemType;
import dslite.world.player.inventory.Slot;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * GUI class for the inventory {@linkplain Slot}s.
 */
public final class InventoryItem extends StackPane {

    private static final Image BG = new Image(InventoryItem.class.getResourceAsStream("/dslite/gui/game/inv_bg.png"));
    private static final BackgroundFill BGFILL = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
    private static final Font ARIAL = new Font("Arial", 24.0);

    private final ImageView item = new ImageView();
    private final Label itemCount = new Label();

    protected InventoryItem() {
        super(new ImageView(BG));
        item.setFitHeight(64.0);
        item.setFitWidth(64.0);
        itemCount.setFont(ARIAL);
        itemCount.setAlignment(Pos.TOP_LEFT);
        itemCount.setBackground(new Background(BGFILL));
        getChildren().addAll(item, itemCount);
    }

    protected void setItem(Slot slot) {
        ItemType type = slot.getStoredItemType();
        if (type != null) {
            item.setImage(Textures.getTexture(type.getSprite()));
            itemCount.setText(String.valueOf(slot.getItemCount()));
        } else {
            item.setImage(null);
            itemCount.setText(null);
        }
    }

    public Label getItemCount() {
        return itemCount;
    }
}

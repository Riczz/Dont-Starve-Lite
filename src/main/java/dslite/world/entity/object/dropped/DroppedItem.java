package dslite.world.entity.object.dropped;

import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;
import dslite.world.entity.object.GameObject;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

/**
 * Represents a dropped {@link Item}.
 * Extends the {@link GameObject} class as the dropped item is visible
 * on the map, inside a {@link TileWithObject}.
 */
public abstract class DroppedItem extends GameObject {

    private final ItemType heldItem;

    /**
     * Constructs a dropped item.
     * <p>Creates a {@link GameObject} of the given {@link ItemType},<br/>
     * but also stores the quantity of the item(s).</p>
     */
    public DroppedItem(ItemType type) {
        super(type.getObjectIndex());
        setSprite(type.getSprite().getIndex());
        heldItem = type;
    }

    @Override
    public void interact(Player player) {
        if (player.getInventory().addItem(heldItem, getQuantity())) {
            TileWithObject t = getTile();
            player.getMap().setTileAtPos(t.getPos(), t.getType());
        }
    }
}

package dslite.world.entity.object.dropped;

import dslite.world.entity.item.ItemType;
import dslite.world.entity.object.GameObject;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

/**
 * Az eldobott tárgyakat leíró osztály.
 * Kiterjeszti a Gameobjectet.
 *
 * @see GameObject
 */
public abstract class DroppedItem extends GameObject {

    /**
     * Az eltárolt Item típusa
     */
    private final ItemType heldItem;

    /**
     * Az eldobott tárgy konstruktora.
     *
     * @param type Az GameObjecthez tartozó Item típus
     * @see ItemType
     */
    public DroppedItem(ItemType type) {
        super(type.getObjectIndex());
        setSprite(type.getSprite().getIndex());
        heldItem = type;
    }

    /**
     * Felülírja az interakció metódust, mivel mindegyik
     * eldobott Itemmel ugyan az történik, a játékos megpróbálja felvenni.
     * Ha sikeres a művelet, átállítja a mezőt üresre.
     *
     * @param player A játékos
     */
    @Override
    public void interact(Player player) {
        if (player.getInventory().addItem(heldItem, getQuantity())) {
            TileWithObject t = getTile();
            player.getMap().setTileAtPos(t.getPos(), t.getType());
        }
    }
}

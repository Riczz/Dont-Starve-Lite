package dslite.world.entity.object;

import dslite.Main;
import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;
import dslite.world.tiles.TileWithObject;

public final class Flower extends GameObject {

    protected Flower() {
        super(ObjectType.FLOWER);
        int[] indexes = type.getSpriteIndexes();
        setSprite(indexes[Main.RAND.nextInt(indexes.length)]);
    }

    @Override
    public void interact(Player player) {
        if (!player.getInventory().addItem(ItemType.PETAL, getQuantity())) return;

        TileWithObject t = getTile();
        player.addSanity(2.0f);
        player.addActionPoints((byte) -1);
        player.getMap().setTileAtPos(t.getPos(), t.getType());
    }
}

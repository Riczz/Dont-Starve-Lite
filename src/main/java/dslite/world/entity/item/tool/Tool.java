package dslite.world.entity.item.tool;

import dslite.controllers.GameController;
import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;

/**
 * Abstract class for Tools.<br/>
 * <p>Every tool has it's own durability.
 * If this value gets below {@code 0}, the Item will be removed.</p>
 */
public abstract class Tool extends Item {

    protected byte durability;

    public Tool(ItemType type, byte durability) {
        super(type);
        this.durability = durability;
    }

    public byte getDurability() {
        return durability;
    };

    public void setDurability(byte durability) {
        this.durability += durability;
        if (getDurability() <= 0) {
            GameController.getPlayer().getInventory().removeItem(this);
        }
    };
}

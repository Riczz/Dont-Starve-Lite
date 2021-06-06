package dslite.world.entity.item.food;

import dslite.controllers.GameController;
import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;
import dslite.world.player.inventory.Inventory;

/**
 * Abstract class describing the edible {@linkplain Item}s.
 */
public abstract class Food extends Item {

    protected float foodValue;
    protected float healthValue;
    protected float sanityValue;
    protected Item cookedForm;

    /**
     * Constructs an edible Item.
     * @param type {@link ItemType} of the food
     * @param cookedForm {@link Food} object for the cooked version
     * @param foodValue hunger value
     * @param healthValue health value
     * @param sanityValue sanity value
     */
    public Food(ItemType type, Food cookedForm, float foodValue, float healthValue, float sanityValue) {
        super(type);
        this.cookedForm = cookedForm;
        this.foodValue = foodValue;
        this.healthValue = healthValue;
        this.sanityValue = sanityValue;
    }

    public void eat() {
        Player player = GameController.getPlayer();
        player.addHunger(foodValue);
        player.addHealth(healthValue);
        player.addSanity(sanityValue);
    }

    public void cook() {
        Inventory inv = GameController.getPlayer().getInventory();
        if (cookedForm == null) return;

        if (inv.removeItemByType(getType(),1) == 1) {
            if (!inv.addItem(cookedForm.getType(),1)) {
                inv.addItem(getType(),1);
            }
            GameController.getPlayer().addActionPoints((byte)-1);
        }
    }
}

package dslite.world.entity.item.food;

import dslite.controllers.GameController;
import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;
import dslite.world.player.inventory.Inventory;

/**
 * Az étel Itemeket leíró osztály.
 * Kiterjeszti az Itemet.
 */
public abstract class Food extends Item {

    protected float foodValue;
    protected float healthValue;
    protected float sanityValue;
    protected Item cookedForm;

    /**
     * Egy étel típusú Item konstruktora.
     * @param type A hozzá tartozó Item típus
     * @param cookedForm A főtt megfelelőhöz tartozó objektum
     * @param foodValue A táplálék érték
     * @param healthValue Az élet érték
     * @param sanityValue Az agy érték
     */
    public Food(ItemType type, Food cookedForm, float foodValue, float healthValue, float sanityValue) {
        super(type);
        this.cookedForm = cookedForm;
        this.foodValue = foodValue;
        this.healthValue = healthValue;
        this.sanityValue = sanityValue;
    }

    /**
     * Evés metódus.
     * Hozzáadja a játékoshoz az
     * eltárolt éhség- élet- és agy értékeket.
     */
    public void eat() {
        Player player = GameController.getPlayer();
        player.addHunger(foodValue);
        player.addHealth(healthValue);
        player.addSanity(sanityValue);
    }

    /**
     * Főzés metódus
     * Ha az étel megfőzhető, akkor elveszi a játékos Inventoryjából
     * ezt az Itemet, és hozzáadja a főtt megfelelőjét.
     */
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

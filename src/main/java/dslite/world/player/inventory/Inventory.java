package dslite.world.player.inventory;

import dslite.gui.inventory.InventoryComponent;
import dslite.world.Updatable;
import dslite.world.entity.item.Craftable;
import dslite.world.entity.item.Item;
import dslite.world.entity.item.ItemType;
import dslite.world.player.Player;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * The inventory of the {@link Player}.
 */
public final class Inventory implements Updatable {

    public static final byte INV_SIZE = 9;
    public static final byte STACK_SIZE = 32;

    private Slot[] slots;
    private Slot selectedSlot;
    private final InventoryComponent invDisplay;

    public Inventory(Player player) {
        invDisplay = player.getController().getInv();
        initSlots();
    }

    /**
     * Calls the {@code update()} method for all {@link Updatable} item inside the inventory.
     */
    @Override
    public void update() {
        for (Slot s : slots) {
            Item item = s.getStoredItem();
            if (item instanceof Updatable) {
                ((Updatable) item).update();
            }
        }
    }

    /**
     * <p>Tries to craft the given item if all of the required items are present and
     * there will be enough space for storing the item after it has been crafted.</p>
     * <p>If the parameter {@code craft} is set to {@code false}, the item won't be crafted,
     * the function will only check if the crafting would be possible or not.</p>
     *
     * @param item the {@link Item} to be crafted
     * @param craft actually craft the item
     * @return {@code true} if the crafting was successful, {@code false} if not
     */
    public boolean tryToCraft(Item item, boolean craft) {
        if (!(item instanceof Craftable)) return false;

        Map<ItemType, Integer> requirements = ((Craftable) item).getRequirements();

        int possibleSlots = (int) requirements
                .entrySet().stream()
                .filter(entry -> getItemCountFor(entry.getKey()) >= entry.getValue())
                .count();

        if (possibleSlots >= requirements.entrySet().size()) {
            if (!craft) return true;
            requirements.forEach((itemType, integer) -> {
                removeItemByType(itemType, integer);
                invDisplay.update();
            });

            if (availableSlotFor(item.getType()) == null) {
                requirements.forEach(this::addItem);
                return false;
            } else {
                addItem(item.getType(), 1);
                return true;
            }
        }
        return false;

    }

    /**
     * Gets the quantity of a specific item inside the inventory.
     * @param type the searched {@link ItemType}
     * @return quantity of the item
     */
    public int getItemCountFor(ItemType type) {
        int count = 0;
        for (Slot s : slots) {
            if (s.getStoredItemType() == type) {
                count += s.getItemCount();
            }
        }
        return count;
    }

    /**
     * Removes a given quantity of a specific {@link ItemType}.
     *
     * @param itemType type of the item to remove
     * @param quantity the amount to remove
     * @return number of items removed successfully
     */
    public int removeItemByType(ItemType itemType, int quantity) {
        int itemsLeft = quantity;

        for (Slot s : slots) {
            if (s.getStoredItemType() == itemType) {
                itemsLeft -= s.add(-itemsLeft);
            }
            if (itemsLeft <= 0) {
                invDisplay.update();
                return quantity;
            }
        }
        invDisplay.update();
        return quantity - itemsLeft;
    }

    /**
     * Removes a slot that contains a specific {@link Item}.
     *
     * @param item the searched item.
     */
    public void removeItem(Item item) {
        Optional<Slot> optionalSlot =
                Arrays.stream(slots)
                        .filter(slot -> slot.getStoredItem() == item)
                        .findFirst();

        optionalSlot.ifPresent(Slot::init);
        invDisplay.update();
    }

    /**
     * Tries to add a given number of items to the inventory.
     * <p>If there is not enough space for the item the items wont be added
     * and the function returns {@code false}.</p>
     *
     * @param itemType {@link ItemType} of the item to add
     * @param quantity the number of items to be added
     * @return {@code true} if the operation was successful, {@code false} if not
     */
    public boolean addItem(ItemType itemType, int quantity) {

        int addedItems = 0;
        while (addedItems < quantity) {
            Slot s = availableSlotFor(itemType);
            if (s == null) {
                removeItemByType(itemType, addedItems);
                invDisplay.update();
                return false;
            }

            if (s.getStoredItemType() == null) {
                s.setStoredItem(itemType);
            }
            addedItems += s.add(quantity-addedItems);
        }
        invDisplay.update();
        return true;
    }

    /**
     * <p>Checks if there is free space for the given {@link ItemType}.
     * The function first searches for slots with the given type that are not full.<br/>
     * If no appropriate slots are found, then it searches for fully empty ones.</p>
     *
     * @return reference to a possible {@link Slot}, or {@code null} if none has been found.
     */
    private Slot availableSlotFor(ItemType type) {
        Optional<Slot> s = Arrays.stream(slots)
                .filter(slot -> {
                    if (slot.getStoredItemType() == type) {
                        return !slot.isFull();
                    }
                    return false;
                }).findFirst();

        if (s.isPresent()) return s.get();

        s = Arrays.stream(slots)
                .filter(slot -> slot.getStoredItemType() == null)
                .findFirst();

        return s.orElse(null);
    }

    private void initSlots() {
        slots = new Slot[INV_SIZE];
        for (int i = 0; i < INV_SIZE; i++) {
            slots[i] = new Slot();
        }
        if (slots.length >= 1) {
            selectedSlot = slots[0];
        }
    }

    public void removeSlot(Slot slot) {
        for (Slot s : slots) {
            if (s == slot) {
                slot.init();
                invDisplay.update();
                return;
            }
        }
        invDisplay.update();
    }

    public Slot getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int index) {
        if (index < 0) return;
        this.selectedSlot = slots[Math.min(INV_SIZE - 1, index)];
        invDisplay.update();
    }

    public Slot getSlot(byte index) {
        return slots[index];
    }

    public Slot[] getSlots() {
        return slots;
    }

    @Override
    public String toString() {
        String ret = "";
        for (Slot s : slots) {
            ret = ret.concat(s.getStoredItemType() == null ? "NULL" : s.getStoredItem() + " " + s.getItemCount());
        }
        return ret;
    }
}

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
 * A játékos eszköztára
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
     * Ha van frissíthető item az inventoryban,
     * frissíti őket.
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
     * Megpróbálja lecraftolni az adott eszközt feltéve,
     * hogy rendelkezésre áll-e a szükséges mennyiségű alapanyag,
     * és a craftolás után lesz-e szabad hely az eszköz eltárolásához.
     *
     * @param item A craftolni kívánt eszköz.
     * @param craft Ténylegesen lecraftolja-e az itemet.
     * @return igaz ha sikerült a craftolás, hamis ha nem.
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

    // Összeszámolja, hogy az adott típusú elemekből hány
    // darab van az inventoryban.
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
     * Az adott típust eltároló slotokból elvesz adott mennyiségű elemet.
     *
     * @param itemType Az eszköz típusa
     * @param quantity Elvenni kívánt mennyiség
     * @return A levont elemek száma
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
     * Inventory slot törlés konkrét Item referencia alapján.
     *
     * @param item a törlendő item
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
     * Megpróbál adott típusú elemből bizonyos mennyiségűt
     * hozzáadni az Inventory tartalmához.
     * @param itemType A hozzáadandó elem típusa
     * @param quantity A hozzáadandó mennyiség
     * @return Sikeres volt-e a művelet
     */
    public boolean addItem(ItemType itemType, int quantity) {

        int addedItems = 0;
        while (addedItems < quantity) {                     //Amíg nem sikerült minden elemet hozzáadni
            Slot s = availableSlotFor(itemType);            //Megnézzük, van-e még szabad hely
            if (s == null) {                                //Ha nincs már üres slot, de még lenne mit hozzáadni
                removeItemByType(itemType, addedItems);     //Kitöröljük az eddig hozzáadott elemeket.
                invDisplay.update();
                return false;                               //És visszatérünk hamissal.
            }

            if (s.getStoredItemType() == null) {            //Ha van üres slot,
                s.setStoredItem(itemType);                  //Az értékét beállítjuk a jelenlegi típusra
            }
            addedItems += s.add(quantity-addedItems);       //És hozzáadjuk a hátralévő mennyiséget
        }
        invDisplay.update();
        return true;
    }

    /**
     * Megvizsgálja, hogy van-e az adott eszköz típushoz szabad slot.
     * Először olyan slotokat keres, amihez már ez az elemtípus tartozik, és még nem üres.
     * Ha nincs ilyen, akkor olyat keres, ami teljesen üres.
     *
     * @param type az eszköz típusa
     * @return sikerült-e találni
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

    /**
     * Metódus ami feltölti a slots tömböt üres Slot objektumokkal.
     */
    private void initSlots() {
        slots = new Slot[INV_SIZE];
        for (int i = 0; i < INV_SIZE; i++) {
            slots[i] = new Slot();
        }
        if (slots.length >= 1) {
            selectedSlot = slots[0];
        }
    }

    /**
     * Adott slot törlése referencia alapján.
     * @param slot A törlendő slot
     */
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

    /**
     * Visszaadja a jelenleg kiválasztott Slot objektumot
     * @return A visszaadott slot
     */
    public Slot getSelectedSlot() {
        return selectedSlot;
    }

    /**
     * Beállítja kiválasztottra az adott indexű Slotot.
     * @param index A slot indexe
     */
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

    /**
     * Tostring metódus teszteléshez
     * @return Az inventory slotjainak tartalma
     */
    @Override
    public String toString() {
        String ret = "";
        for (Slot s : slots) {
            ret = ret.concat(s.getStoredItemType() == null ? "NULL" : s.getStoredItem() + " " + s.getItemCount());
        }
        return ret;
    }
}

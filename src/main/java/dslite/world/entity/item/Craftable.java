package dslite.world.entity.item;

import java.util.Map;

/**
 * Interface for {@linkplain Item}s that can be crafted.
 */
public interface Craftable {

    /**
     * Gets the crafting requirements.
     * @return map containing the required {@linkplain ItemType}s as keys<br/>
     * and the required quantity as values.
     */
    Map<ItemType, Integer> getRequirements();
}

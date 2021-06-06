package dslite.world.entity.object;

import dslite.world.entity.object.dropped.*;

public final class ObjectInfo {

    private ObjectInfo() {}

    /**
     * Returns a {@code GameObject} for the given id.
     * @param index id for the game object
     * @return object reference
     * @see ObjectType
     */
    public static GameObject getObject(int index) {
        switch(index) {
            case 1: return new Evergreen();
            case 2: return new Sapling();
            case 3: return new Flower();
            case 4: return new Grass();
            case 5: return new Bush();
            case 6: return new Boulder();
            case 7: return new GoldVein();
            case 8: return new BoulderFlintless();
            case 9: return new SpikyTree();
            case 10: return new SpikyBush();
            case 11: return new LumpyEvergreen();
            case 12: return new RocksObj();
            case 13: return new GoldObj();
            case 14: return new CarrotObj();
            case 15: return new FlintObj();
            case 16: return new CampfireObj();
            case 17: return new CutGrassDropped();
            case 18: return new LogDropped();
            case 19: return new PetalDropped();
            case 20: return new TwigsDropped();
            case 21: return new BerriesDropped();
            case 22: return new CarrotDropped();
            case 23: return new GarlandDropped();
            case 24: return new AxeDropped();
            case 25: return new PickaxeDropped();
            case 26: return new CookedBerriesDropped();
            case 27: return new CookedCarrotDropped();
            default: return null;
        }
    }
}

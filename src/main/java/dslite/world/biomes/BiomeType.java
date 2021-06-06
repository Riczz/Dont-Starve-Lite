package dslite.world.biomes;

import dslite.world.entity.object.GameObject;
import dslite.world.tiles.TileType;
import java.util.Map;

/**
 * Enum containing the {@link Biome} types.
 */
public enum BiomeType {
    FOREST(TileType.FOREST, Map.of(1, 0.5f, 11, 0.3f, 15, 0.05f, 14, 0.05f, 5, 0.02f, 4, 0.1f, 2, 0.1f, 3, 0.1f)),
    GRASSLANDS(TileType.GRASS, Map.of(15, 0.15f, 14, 0.15f, 4, 0.6f, 1, 0.2f, 2, 0.25f, 5, 0.2f, 6, 0.05f, 3, 0.03f)),
    MARSH(TileType.MARSH, Map.of(9, 0.45f, 10, 0.2f)),
    SAVANNA(TileType.SAVANNA, Map.of(15, 0.2f, 14, 0.15f, 4, 0.75f, 6, 0.05f, 3, 0.05f)),
    DESERT(TileType.SAND, Map.of(8, 0.3f, 6, 0.2f, 15, 0.05f, 4, 0.1f)),
    ROCKYLAND(TileType.ROCKY, Map.of(15, 0.35f, 6, 0.4f, 7, 0.25f, 13, 0.1f, 12, 0.2f));

    private final TileType type;
    private final Map<Integer, Float> spawnrates;

    public static final BiomeType[] VAL = values();
    public static final int LEN = values().length;

    /**
     * Constructs a new biome type.
     * @param type the {@link TileType} the biome is made of.
     * @param spawnrates spawnrates for specific {@link GameObject} id-s.
     * @see dslite.world.entity.object.ObjectInfo
     */
    BiomeType(TileType type, Map<Integer, Float> spawnrates) {
        this.type = type;
        this.spawnrates = spawnrates;
    }

    public Map<Integer, Float> getSpawnrates() {
        return spawnrates;
    }

    public TileType getTileType() {
        return type;
    }

}

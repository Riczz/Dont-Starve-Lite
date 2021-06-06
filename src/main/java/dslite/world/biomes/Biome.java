package dslite.world.biomes;

import dslite.world.tiles.Tile;
import dslite.world.tiles.TileType;

import java.util.HashMap;

/**
 * Contains information about the biomes in the world.
 * Only used during map generation, the objects get deleted afterwards.
 *
 * @see dslite.world.WorldMap
 * @see BiomeType
 * @see Tile
 * @see TileType
 */
public final class Biome {

    private TileType tileType;
    private final BiomeType biomeType;

    private final Point base;                                        //The center position of the biome
    private final HashMap<Point, Tile> tiles;                        //The contained tiles and their positions

    public Biome(BiomeType type, int x, int y) {
        this.biomeType = type;
        this.tileType = type.getTileType();
        this.base = new Point(x, y);
        this.tiles = new HashMap<>();
    }

    /**
     * Adds a {@link Tile} to the biome.
     *
     * @param t the reference to be added
     * @param x x position
     * @param y y position
     * @see Tile
     */
    public void addToList(Tile t, int x, int y) {
        tiles.put(new Point(x, y), t);
    }

    /**
     * Changes the {@link TileType} of all of the contained tiles to a specific type.
     *
     * @param biome the biome to change
     * @param tileType the type of the biome
     */
    public static void setTileType(Biome biome, TileType tileType) {
        biome.tiles.forEach((point, tile) -> tile.setType(tileType));
        biome.tileType = tileType;
    }

    public HashMap<Point, Tile> getTiles() {
        return tiles;
    }

    public BiomeType getBiomeType() {
        return biomeType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public Point getBase() {
        return base;
    }

}

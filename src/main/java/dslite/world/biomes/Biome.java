package dslite.world.biomes;

import dslite.world.tiles.Tile;
import dslite.world.tiles.TileType;

import java.util.HashMap;

/**
 * A mapot felépítő régiókat leíró osztály, ezeknek az adatait tárolja.
 * Csak a map generálásakor van szerepe, utána törlődnek a létrehozott objektumok.
 *
 * @see dslite.world.WorldMap
 * @see BiomeType
 * @see Tile
 * @see TileType
 */
public final class Biome {

    private TileType tileType;
    private final BiomeType biomeType;

    private final Point base;                                        //Generálópont,a Biome "középpontja"
    private final HashMap<Point, Tile> tiles;                        //A Biome-hoz tartozó Tile-ok és azok pozíciói

    /**
     * A Biome konstruktora
     *
     * @param type A Biome típusa
     * @param x    A középpont x koordinátája
     * @param y    A középpont y koordinátája
     */
    public Biome(BiomeType type, int x, int y) {
        this.biomeType = type;
        this.tileType = type.getTileType();
        this.base = new Point(x, y);
        this.tiles = new HashMap<>();
    }

    /**
     * Egy tile hozzáadása a biomehoz,
     * Map generálás közben használatos.
     *
     * @param t A Tile
     * @param x Vízszintes pozíció
     * @param y Függőleges pozíció
     */
    public void addToList(Tile t, int x, int y) {
        tiles.put(new Point(x, y), t);
    }

    /**
     * Kicseréli a biomehoz tartozó Tile-okat más típusúra.
     *
     * @param tileType A típus, amire le lesz cserélve a régi
     */
    public void setTileType(TileType tileType) {
        tiles.forEach((point, tile) -> tile.setType(tileType));
        this.tileType = tileType;
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

package dslite.world;

import dslite.Main;
import dslite.controllers.GameController;
import dslite.controllers.MenuController;
import dslite.world.biomes.Biome;
import dslite.world.biomes.BiomeType;
import dslite.world.biomes.Point;
import dslite.world.entity.Textures;
import dslite.world.entity.object.GameObject;
import dslite.world.tiles.Tile;
import dslite.world.tiles.TileType;
import dslite.world.tiles.TileWithObject;

import java.util.*;

/**
 * The map generator class.
 *
 * @see World
 * @see dslite.world.player.Player
 * @see MenuController
 */
public final class WorldMap {

    private final int width;
    private final int height;

    private Tile[][] tilemap;
    private final List<GameObject> updatableObjects;

    private Point spawnPoint;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;

        updatableObjects = new ArrayList<>();
        Textures.getInstance();
        generateMap();
    }

    /**
     * The updatable {@linkplain GameObject}s will be gathered here.
     * The method calls the {@code update()} method for every object.
     */
    public void update() {
        for (GameObject obj : updatableObjects) {
            if (obj instanceof Updatable) {
                ((Updatable) obj).update();
            }
        }
        GameController.getGrid().drawPlayer(GameController.getPlayer());
    }

    /**
     * Method for map generation
     * The procedure is based on voronoi diagrams in some sort.
     */
    //TODO: optimize memory usage and speed
    public void generateMap() {
        List<Biome> biomes = new ArrayList<>();
        Set<Point> biomePoints = new HashSet<>(Collections.emptySet());

        int biomeNum = 0;
        switch(MenuController.getBiomeSize()) {
            case "Small": {
                biomeNum = (int) (width*width * 0.025);
                break;
            }
            case "Medium": {
                biomeNum = (int) (width*width * 0.01);
                break;
            }
            case "Large": {
                biomeNum = (int) (width*width * 0.005);
                break;
            }
        }

        biomeNum += Main.RAND.nextInt(100);

        while (biomePoints.size() < biomeNum) {
            biomePoints.add(new Point(Main.RAND.nextInt(width - 1), Main.RAND.nextInt(height - 1)));
        }

        for (Point p : biomePoints) {
            biomes.add(new Biome(BiomeType.VAL[Main.RAND.nextInt(BiomeType.LEN)], p.getX(), p.getY()));
        }

        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                Biome nearestBiome = null;
                int dist = Integer.MAX_VALUE;

                for (Biome b : biomes) {
                    int cDist = Point.cDist(b.getBase(), i, j);
                    if (cDist < dist) {
                        nearestBiome = b;
                        dist = cDist;
                    }
                }
                assert nearestBiome != null;
                nearestBiome.addToList(new Tile(nearestBiome.getTileType()), i, j);
            }
        }

        //TODO: organize this somehow
        initTilemap(TileType.WATER);
        chunkBorders(biomes);
        fillWithResources(biomes);
        makeTilemap(biomes);
        createSpawnPoint(biomes);

        //Memory cleaning
        biomePoints = null;
        biomes = null;
        System.gc();
    }

    /**
     * Replaces the tiles of biomes which areas touch the border of the map to water.
     * This makes sure the edge of the map is always surrounded with water.
     */
    private void chunkBorders(List<Biome> biomes) {
        biomes.stream().filter(biome -> biome
                .getTiles()
                .keySet()
                .stream().anyMatch(point -> {
                    int x = point.getX();
                    int y = point.getY();
                    return x == width - 2 || x == 0 || y == height - 2 || y == 0;  // Ha a biome bármelyik eleme túlnyúlik a határon,
                })).forEach(border -> Biome.setTileType(border, TileType.WATER));         // Kicseréli az összes elemét vízre,
        biomes.removeIf(biome -> biome.getTileType().equals(TileType.WATER));      // Végül töröljük őket.
    }

    /**
     * Fills the map with resources based on the {@linkplain BiomeType}s spawn rates.
     */
    private void fillWithResources(List<Biome> biomes) {
        for (Biome b : biomes) {
            Map<Integer, Float> spawnrates = b.getBiomeType().getSpawnrates();

            b.getTiles().replaceAll((point, tile) -> {
                //Half of the tiles will always be empty
                if (Main.RAND.nextFloat() > 0.5f) {
                    return tile;
                }

                Optional<Map.Entry<Integer, Float>> newTile = spawnrates
                        .entrySet()
                        .stream()
                        .filter(entry -> Main.RAND.nextFloat() <= entry.getValue())
                        .findAny();

                if (newTile.isPresent()) {
                    int objIndex = newTile.get().getKey();
                    return new TileWithObject(b.getTileType(), point, objIndex);
                }

                return tile;
            });
        }
    }

    /**
     * Creates a spawn point for the player.<br/>
     * Selects the first position which is not solid.
     * @see TileType#WATER
     */
    private void createSpawnPoint(List<Biome> biomes) {
        for (Biome b : biomes) {
            Optional<Map.Entry<Point, Tile>> entry =
                    b.getTiles()
                            .entrySet()
                            .stream()
                            .filter(tile -> !tile.getValue().getType().isSolid()).findFirst();
            if (entry.isPresent()) {
                setSpawnPoint(entry.get().getKey());
                return;
            }
        }
    }

    private void makeTilemap(List<Biome> biomes) {
        for (Biome b : biomes) {
            b.getTiles().forEach((point, tile) ->
                    tilemap[point.getX()][point.getY()] = tile);
        }
    }

    private void initTilemap(TileType type) {
        tilemap = new Tile[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tilemap[i][j] = new Tile(type);
            }
        }
    }

    public void setTileAtPos(Point p, TileType type) {
        tilemap[p.getX()][p.getY()] = new Tile(type);
    }

    public void removeFromUpdatable(GameObject obj) {
        updatableObjects.remove(obj);
    }

    public void addToUpdatable(GameObject obj) {
        updatableObjects.add(obj);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Point spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public Tile[][] getTilemap() {
        return tilemap;
    }

}

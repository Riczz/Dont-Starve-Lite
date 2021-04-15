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
 * A Világhoz tartozó map.
 * Itt készül el a játszható pálya a beállított értékek alapján.
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
     * Frissítés metódus.
     * Az Updatable interface-el rendelkező GameObjectek miután létrehozzuk őket hozzáadják magukat a listához.
     * Ez a metódus végigjárja őket, és meghívja a GameObjectek update metódusait.
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
     * A map generálását megvalósító metódus.
     */
    public void generateMap() {
        List<Biome> biomes = new ArrayList<>();
        Set<Point> biomePoints = new HashSet<>(Collections.emptySet());

        // A kiválasztott biome méretnek megfelelő számú generálópont választása
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

        // Kis szórás hozzáadása
        biomeNum += Main.RAND.nextInt(100);

        // Generálópontok létrehozása
        while (biomePoints.size() < biomeNum) {
            biomePoints.add(new Point(Main.RAND.nextInt(width - 1), Main.RAND.nextInt(height - 1)));
        }

        // Minden ponthoz beállít egy véletlen típusú Biome-ot adott középponttal.
        for (Point p : biomePoints) {
            biomes.add(new Biome(BiomeType.VAL[Main.RAND.nextInt(BiomeType.LEN)], p.getX(), p.getY()));
        }

        // A map minden Tile-jára végignézi, hogy melyik pont esik hozzá a legközelebb, és annak megfelelően állítja be a
        // típusát. Egyszerű buborékrendezés, egyáltalán nem hatékony, de működik.
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

        // Hogy ne legyen hosszabb, mint 100 sor :p
        initTilemap(TileType.WATER);
        chunkBorders(biomes);
        fillWithResources(biomes);
        makeTilemap(biomes);
        createSpawnPoint(biomes);

        // Memória takarítás
        biomePoints = null;
        biomes = null;
        System.gc();
    }

    /**
     * A kapott listában található Biome-ok közül törli azokat, amik túlnyúlnak a
     * Map határán (vízre cseréli ki az elemeit).
     *
     * @param biomes A lista, amin a művelet végrehajtódik.
     */
    private void chunkBorders(List<Biome> biomes) {
        biomes.stream().filter(biome -> biome
                .getTiles()
                .keySet()
                .stream().anyMatch(point -> {
                    int x = point.getX();
                    int y = point.getY();
                    return x == width - 2 || x == 0 || y == height - 2 || y == 0;  // Ha a biome bármelyik eleme túlnyúlik a határon,
                })).forEach(border -> border.setTileType(TileType.WATER));         // Kicseréli az összes elemét vízre,
        biomes.removeIf(biome -> biome.getTileType().equals(TileType.WATER));      // Végül töröljük őket.
    }

    /**
     * Feltölti a mapot nyersanyagokkal a típusban meghatározott spawnolási értékeknek megfelelően.
     *
     * @param biomes A lista, amin a művelet végrehajtódik.
     * @see BiomeType
     */
    private void fillWithResources(List<Biome> biomes) {
        for (Biome b : biomes) {
            Map<Integer, Float> spawnrates = b.getBiomeType().getSpawnrates();

            b.getTiles().replaceAll((point, tile) -> {
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
     * Spawn point választása a játékos számára.
     * Megkeresi a legelső olyan pozíciót, ami nem szolid, és azt választja ki.
     * (A Biomek mezőin nincs szolid elem, de eredetileg lett volna ilyen is, ezért maradt benne).
     *
     * @param biomes A lista, amin a művelet végrehajtódik.
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

    /**
     * A map feltöltése Tile-okkal.
     *
     * @param biomes A lista, amin a művelet végrehajtódik.
     */
    private void makeTilemap(List<Biome> biomes) {
        for (Biome b : biomes) {
            b.getTiles().forEach((point, tile) ->
                    tilemap[point.getX()][point.getY()] = tile);
        }
    }

    /**
     * A map inicializálása üres Tile objektumokkal.
     *
     * @param type A Tile típusa
     */
    private void initTilemap(TileType type) {
        tilemap = new Tile[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tilemap[i][j] = new Tile(type);
            }
        }
    }

    /**
     * Adott pozíción lévő Tile lecserélése.
     *
     * @param p    A Tile pozíciója
     * @param type A típus, amire lecserélődik
     */
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

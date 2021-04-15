package dslite.world.biomes;

/**
 * Egy pontot leíró objektum.
 * Lehett volna használni a beépített osztályt is, de az nem lenne gazdaságos.
 *
 * @see javafx.geometry.Point2D
 */
public final class Point {
    private final int x;
    private final int y;

    /**
     * Egy pont konstruktora
     *
     * @param x A pont x koordinátája
     * @param y A pont y koordinátája
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Statikus függvény Manhattan-távolság kiszámítására.
     *
     * @return Két pont közötti Manhattan-távolság
     * @see dslite.world.WorldMap
     */
    public static int mDist(Point p, int x2, int y2) {
        int xDiff = x2 - p.x;
        int yDiff = y2 - p.y;
        return Math.abs(xDiff) + Math.abs(yDiff);
    }

    /**
     * Statikus függvény Euklideszi-távolság kiszámítására.
     *
     * @return Két pont közötti Euklideszi-távolság
     * @see dslite.world.WorldMap
     */
    public static int cDist(Point p, int x2, int y2) {
        int xDiff = x2 - p.x;
        int yDiff = y2 - p.y;
        return xDiff * xDiff + yDiff * yDiff;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

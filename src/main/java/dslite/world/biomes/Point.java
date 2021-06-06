package dslite.world.biomes;

/**
 * Represents a 2D point.
 * Used for giving positions and in map generation.
 *
 * @see dslite.world.WorldMap
 */
public final class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates Manhattan distance between two points.
     *
     * @see dslite.world.WorldMap
     */
    public static int mDist(Point p, int x2, int y2) {
        int xDiff = x2 - p.x;
        int yDiff = y2 - p.y;
        return Math.abs(xDiff) + Math.abs(yDiff);
    }

    /**
     * Calculates euclidean distance between two points.
     *
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

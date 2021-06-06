package dslite.world;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface for objects that can be drawn on the game grid.
 */
public interface Drawable {

    void draw(GraphicsContext gc, int i, int j);

}

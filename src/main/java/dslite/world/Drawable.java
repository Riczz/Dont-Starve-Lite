package dslite.world;

import javafx.scene.canvas.GraphicsContext;

/**
 * Kirajzolható interfész
 */
public interface Drawable {

    /**
     * Az átadott paraméterekkel kirajzolja a világban
     * az i és j koordinátákon található mezőket.
     * @param gc Konfigurálható GraphicsContext
     * @param i X koordináta
     * @param j Y koordináta
     */
    void draw(GraphicsContext gc, int i, int j);

}

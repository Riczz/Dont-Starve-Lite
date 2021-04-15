package dslite.gui.menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A menüben megjelenő nyilat leíró osztály.
 * Hozzáköthető az ArrowButtonhoz
 *
 * @see ArrowButton
 * @see ArrowMenu
 */
public final class Arrow extends ImageView {

    private final boolean previous;
    private static final String PATH = "/dslite/gui/play_inactive.png";
    private static final Image IMG = new Image(Arrow.class.getResource(PATH).toExternalForm());

    /**
     * A nyíl konstruktora
     * @param isPrevious Visszafelé mutató-e
     */
    public Arrow(boolean isPrevious) {
        super();
        setFitWidth(30);
        setFitHeight(40);
        setImage(IMG);
        previous = isPrevious;
        if (isPrevious) { setRotate(180.0); }
    }

    /**
     * Hozzáköti a nyilat a gombhoz.
     * @param btn A gomb, amihez hozzákötődik
     */
    public void linkToButton(ArrowButton btn) {
        this.setOnMouseClicked(mouseEvent ->
                btn.setText(isPrevious() ? btn.getPreviousOption() : btn.getNextOption()));
    }

    public boolean isPrevious() {
        return previous;
    }

}

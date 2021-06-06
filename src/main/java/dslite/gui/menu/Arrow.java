package dslite.gui.menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * GUI class of the arrow symbol of {@link ArrowMenu}.
 *
 * @see ArrowButton
 */
public final class Arrow extends ImageView {

    private final boolean previous;
    private static final String PATH = "/dslite/gui/play_inactive.png";
    private static final Image IMG = new Image(Arrow.class.getResource(PATH).toExternalForm());

    /**
     * Constructs an arrow.
     * If <code>isPrevious</code> is set to <code>true</code><br/>
     * the image gets rotated by 180 degrees.
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
     * Links the arrow to an {@link ArrowButton}.
     */
    public void linkToButton(ArrowButton btn) {
        this.setOnMouseClicked(mouseEvent ->
                btn.setText(isPrevious() ? btn.getPreviousOption() : btn.getNextOption()));
    }

    public boolean isPrevious() {
        return previous;
    }

}

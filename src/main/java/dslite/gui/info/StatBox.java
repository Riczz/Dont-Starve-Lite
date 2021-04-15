package dslite.gui.info;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Locale;

/**
 * Valamilyen tulajdonsághoz tartozó értéket megjelenítő doboz.
 * Az értékhez tartozik egy maximum érték, és miközben annak értéke változik,
 * annyival jobban eltér a piros irányába az szöveg színe.
 *
 * @see StatBoxSimple
 */
public class StatBox extends HBox {

    protected final Label label;                                    //A tulajdonság
    protected final Label value;                                    //Az érték
    private float maxVal;

    private static final Color GREEN = new Color(0.0, 1.0, 0.0, 1.0);
    private static final double MAXHUE = GREEN.getHue();
    private static final BackgroundFill BGFILL = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);

    /**
     * Konstruktor, ami beállítja a tulajdonságot.
     * @param text A tulajdonság neve
     */
    protected StatBox(String text) {
        super();
        maxVal = 0.0f;
        prefWidth(100.0);
        prefHeight(100.0);

        label = new Label(text);
        label.setFont(Font.font("Arial", 32.0));
        label.setTextFill(Color.WHITE);
        label.setBackground(new Background(BGFILL));
        label.setAlignment(Pos.CENTER_LEFT);

        value = new Label();
        value.setFont(Font.font("Arial", 32.0));
        value.setBackground(new Background(BGFILL));
        value.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(label, value);
    }

    /**
     * Konstruktor, ami beállítja a tulajdonságot, és a hozzá tartozó maximális értéket.
     * @param text A tulajdonság neve
     * @param maxVal A maximális érték
     */
    protected StatBox(String text, float maxVal) {
        this(text);
        this.maxVal = maxVal;
    }

    /**
     * Megváltoztatja az értéket, és ennek megfelelően eltolja a szöveg színét a piros irányába.
     * @param val Az új érték
     */
    protected void setValue(float val) {
        value.setText(String.format(Locale.ROOT, "%.1f", val));
        value.setTextFill(Color.hsb(Math.max(MAXHUE * val / maxVal, 0), 1.0, 1.0));
    }
}

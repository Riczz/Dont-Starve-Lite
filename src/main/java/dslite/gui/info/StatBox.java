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
 * Stat box for displaying a value in a range.<br/>
 * The color of the text changes between red and green depending on the current value.
 *
 * @see StatBoxSimple
 */
public class StatBox extends HBox {

    protected final Label label;                                    //Stat name to display
    protected final Label value;                                    //Value
    private float maxVal;

    private static final Color GREEN = new Color(0.0, 1.0, 0.0, 1.0);
    private static final double MAXHUE = GREEN.getHue();
    private static final BackgroundFill BGFILL = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);

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

    protected StatBox(String text, float maxVal) {
        this(text);
        this.maxVal = maxVal;
    }

    protected void setValue(float val) {
        value.setText(String.format(Locale.ROOT, "%.1f", val));
        value.setTextFill(Color.hsb(Math.max(MAXHUE * val / maxVal, 0), 1.0, 1.0));
    }
}

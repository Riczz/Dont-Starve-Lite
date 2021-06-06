package dslite.gui.info;

import javafx.scene.paint.Color;

import java.util.Locale;

/**
 * {@link StatBox} for displaying a value.<br/>
 * The color of the text is always white.
 * */
public final class StatBoxSimple extends StatBox {
    protected StatBoxSimple(String text) {
        super(text);
    }

    @Override
    protected void setValue(float val) {
        value.setText(String.format(Locale.ROOT, "%.0f", val));
        value.setTextFill(Color.WHITE);
    }
}

package dslite.gui.info;

import javafx.scene.paint.Color;

import java.util.Locale;

/**
 * Ugyan az mint a StatBox, csak nem tartozik hozzá maximum érték,
 * és nem színeződik a szöveg.
 *
 * @see StatBox
 */
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

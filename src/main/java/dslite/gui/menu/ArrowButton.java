package dslite.gui.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

/**
 * A nyilas menüben középen megjelenő gomb.
 * Egy Observablelistben tartalmazza Stringként az opciókat,
 * amik közt a nyílakra kattintva lehet váltani.
 *
 * @see Arrow
 * @see ArrowMenu
 */
public final class ArrowButton extends Button {

    private ObservableList<String> options = FXCollections.observableArrayList();
    private int selectedOption;

    public ArrowButton() {
        super();
        setMinWidth(150.0);
        setMinHeight(50.0);
        setFocusTraversable(false);
    }

    /**
     * Visszaadja az opciók közül az egyel kisebb indexűt, ha van ilyen.
     * @return Az előző opció
     */
    public String getPreviousOption() {
        if (selectedOption > 0) {
            return options.get(--selectedOption);
        }
        return options.get(selectedOption);
    }

    /**
     * Visszaadja az opciók közül az egyel nagyobb indexűt, ha van ilyen.
     * @return A következő opció
     */
    public String getNextOption() {
        if (selectedOption < options.size() - 1) {
            return options.get(++selectedOption);
        }
        return options.get(selectedOption);
    }

    /**
     * A paraméteren keresztül beállítható, hogy melyik opció legyen kiválasztva.
     * @param index Az elem indexe
     */
    public void setSelectedOption(int index) {
        selectedOption = index;
        setText(options.get(index));
    }

    /**
     * A gombhoz hozzárendeli az opciókat
     * @param options Az opciókat tartalmazó lista
     */
    public void setOptions(ObservableList<String> options) {
        this.options = options;
        setSelectedOption(0);
    }

}

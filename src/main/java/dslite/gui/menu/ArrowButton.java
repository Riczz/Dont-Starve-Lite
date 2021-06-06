package dslite.gui.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

/**
 * The displayed button of the {@link ArrowMenu}.<br/>
 * Contains the selectable options in a {@link ObservableList}.
 *
 * @see Arrow
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

    public String getPreviousOption() {
        if (selectedOption > 0) {
            return options.get(--selectedOption);
        }
        return options.get(selectedOption);
    }

    public String getNextOption() {
        if (selectedOption < options.size() - 1) {
            return options.get(++selectedOption);
        }
        return options.get(selectedOption);
    }

    public void setSelectedOption(int index) {
        selectedOption = index;
        setText(options.get(index));
    }

    public void setOptions(ObservableList<String> options) {
        this.options = options;
        setSelectedOption(0);
    }

}

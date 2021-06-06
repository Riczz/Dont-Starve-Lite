package dslite.gui.menu;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * GUI Menu that can be cycled with {@linkplain Arrow}s.
 *
 * @see ArrowButton
 * @see dslite.controllers.MenuController
 */
public final class ArrowMenu extends HBox {

    private final Arrow prev = new Arrow(true);
    private final Arrow next = new Arrow(false);
    private final ArrowButton btn = new ArrowButton();

    public ArrowMenu() {
        super();
        setAlignment(Pos.CENTER);
        setWidth(190.0);
        setHeight(50.0);
        setSpacing(5.0);

        prev.linkToButton(btn);
        next.linkToButton(btn);
        getChildren().addAll(prev, btn, next);
    }

    public ArrowButton getMenuButton() {
        return btn;
    }
}

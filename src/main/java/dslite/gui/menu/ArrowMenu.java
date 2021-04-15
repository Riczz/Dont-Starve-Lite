package dslite.gui.menu;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Nyilakkal váltogatható menü, a főmenüben található.
 * Tartalmaz két nyilat, középen egy gombot, a nyilak segítségével
 * lehet váltani az ArrowButtonhoz hozzárendelt opciók közt.
 *
 * @see Arrow
 * @see ArrowButton
 * @see dslite.controllers.MenuController
 */
public final class ArrowMenu extends HBox {

    private final Arrow prev = new Arrow(true);
    private final Arrow next = new Arrow(false);
    private final ArrowButton btn = new ArrowButton();

    /**
     * A nyilas menü konstruktora.
     * Beállítja a méreteket, és hozzáadja az elemeket.
     */
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

package dslite.controllers;

import dslite.Main;
import dslite.gui.menu.ArrowMenu;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Controller for the main menu.
 */
public final class MenuController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Rectangle minBtn;

    @FXML
    private TextField seedField;

    @FXML
    private HBox wSizeBox;

    @FXML
    private HBox bSizeBox;

    @FXML
    private Button generateBtn;

    private static String biomeSize;                                //The selected biome size
    private static int sizeX;                                       //World width
    private static int sizeY;                                       //World height

    private double xOffset;                                         //Mouse X pos
    private double yOffset;                                         //Mouse Y pos
    private Stage stage;

    @FXML
    private final ObservableList<String> wSizeOptions = FXCollections.observableArrayList(
            "128x128", "256x256", "512x512"
    );

    @FXML
    private final ObservableList<String> bSizeOptions = FXCollections.observableArrayList(
            "Small", "Medium", "Large"
    );

    /**
     * Validates the seed field.
     * If it contains at least one alphabetic character or at least 20 digits,
     * a hash value will be generated from it.<br/>
     * Otherwise the original value will be used.
     */
    @FXML
    private void seedCheck(String seed) {
        if (Pattern.matches("[0-9]{1,19}", seed)) {
            Main.RAND.setSeed(Long.parseLong(seed));
        } else if (!seed.equals("")) {
            Main.RAND.setSeed(seed.hashCode());
        }
    }

    /**
     * Starts the game with the selected settings.
     */
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        seedCheck(seedField.getText());

        Stage gameStage = new Stage(StageStyle.DECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dslite/gamescreen.fxml"));
        Parent root = fxmlLoader.load();

        gameStage.setScene(new Scene(root));
        gameStage.setWidth(1600.0);
        gameStage.setHeight(900.0);
        gameStage.setTitle("Dont Starve Lite");
        gameStage.getIcons().add(new Image(getClass().getResourceAsStream("/dslite/gui/icon.png")));

        gameStage.setOnHidden(request -> {
            getStage(mainPane).show();
            gameStage.close();
        });
        getStage((Node) event.getSource()).hide();
        gameStage.showAndWait();
    }

    @FXML
    public void initialize() {
        sizeX = getResolution(wSizeOptions.get(0), 0);
        sizeY = getResolution(wSizeOptions.get(0), 1);
        biomeSize = bSizeOptions.get(0);

        ArrowMenu wSizeMenu = new ArrowMenu();
        ArrowMenu bSizeMenu = new ArrowMenu();
        wSizeMenu.getMenuButton().setOptions(wSizeOptions);
        bSizeMenu.getMenuButton().setOptions(bSizeOptions);
        wSizeMenu.getMenuButton().textProperty().addListener((observableValue, s, newVal) -> {
            sizeX = getResolution(newVal, 0);
            sizeY = getResolution(newVal, 1);
        });
        bSizeMenu.getMenuButton().textProperty().addListener((observableValue, s, newVal) -> this.biomeSize = newVal);
        wSizeBox.getChildren().add(wSizeMenu);
        bSizeBox.getChildren().add(bSizeMenu);
        seedField.setTooltip(new Tooltip("World seed"));
        generateBtn.setTooltip(new Tooltip("Generate map"));
    }

    @FXML
    private void handlePressEvent(MouseEvent event) {
        stage = getStage(mainPane);
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    @FXML
    private void handleDragEvent(MouseEvent event) {
        stage = getStage(mainPane);
        if (event.isPrimaryButtonDown()) {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        }
    }

    @FXML
    private void minimize() {
        stage = getStage(minBtn);
        stage.setIconified(true);
    }

    @FXML
    private void closeApp() {
        Platform.exit();
    }

    private Stage getStage(Node node) {
        return stage = (Stage) node.getScene().getWindow();
    }

    public static int getSizeX() {
        return sizeX;
    }

    public static int getSizeY() {
        return sizeY;
    }

    public static String getBiomeSize() {
        return biomeSize;
    }

    private static Integer getResolution(String res, int index) {
        return Integer.parseInt(res.split("x")[index]);
    }

}

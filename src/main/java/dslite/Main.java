package dslite;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Random;

/**
 * JavaFX version of Don't starve game.
 *
 * @author Riczz
 * @version 1.0
 */
public final class Main extends Application {

    /**
     * Random number generator used in map generation.
     *
     * @see dslite.world.WorldMap
     * @see Random
     */
    public final static Random RAND = new Random();

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("menu"));
        scene.getStylesheets().add(this.getClass().getResource("css/style.css").toExternalForm());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setWidth(625.0);
        stage.setHeight(400.0);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
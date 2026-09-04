package jelly;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/** Starts Jelly's JavaFX application. */
public class Main extends Application {

    private final Jelly jelly = new Jelly();

    /**
     * Loads and displays the FXML-based Jelly window.
     *
     * @param stage the JavaFX application window.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/MainWindow.fxml"));
            Scene scene = new Scene(loader.load());

            MainWindow controller = loader.getController();
            Image userImage = new Image(
                    getClass().getResourceAsStream("/images/kuromi.jpg"));
            Image jellyImage = new Image(
                    getClass().getResourceAsStream("/images/hangyodon.png"));

            controller.setJelly(jelly);
            controller.setImages(userImage, jellyImage);

            stage.setTitle("Jelly");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load MainWindow.fxml", e);
        }
    }
}

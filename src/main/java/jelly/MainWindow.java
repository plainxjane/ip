package jelly;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/** Controls the main Jelly window defined in MainWindow.fxml. */
public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox messageArea;

    @FXML
    private TextField inputField;

    private Jelly jelly;
    private Image userImage;
    private Image jellyImage;

    /** Configures controls after FXML has injected them. */
    @FXML
    private void initialize() {
        messageArea.setPadding(new Insets(10));
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageArea.heightProperty().addListener((observable,
                                                  oldHeight, newHeight) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Injects the Jelly command-processing instance.
     *
     * @param jelly the command-processing instance
     */
    public void setJelly(Jelly jelly) {
        this.jelly = jelly;
    }

    /**
     * Injects the user and Jelly avatar images.
     *
     * @param userImage the user's avatar
     * @param jellyImage Jelly's avatar
     */
    public void setImages(Image userImage, Image jellyImage) {
        this.userImage = userImage;
        this.jellyImage = jellyImage;
    }

    /** Processes the command entered by the user. */
    @FXML
    private void handleInput() {
        String input = inputField.getText();
        if (input.isBlank()) {
            return;
        }

        messageArea.getChildren().add(DialogBox.getUserDialog(input, userImage));
        String response = jelly.executeCommand(input);
        messageArea.getChildren().add(DialogBox.getJellyDialog(response, jellyImage));
        inputField.clear();
    }
}

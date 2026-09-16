package jelly;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Controls the main Jelly window defined in MainWindow.fxml. */
public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox messageArea;

    @FXML
    private TextField inputField;

    private Jelly jelly;
    private Stage stage;
    private Image userImage;
    private Image jellyImage;
    private boolean shouldAutoScroll;

    /** Configures controls after FXML has injected them. */
    @FXML
    private void initialize() {
        messageArea.setPadding(new Insets(12));
        messageArea.setFillWidth(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**
     * Injects the Jelly command-processing instance.
     *
     * @param jelly the command-processing instance.
     */
    public void setJelly(Jelly jelly) {
        this.jelly = jelly;
    }

    /** Injects the JavaFX window controlled by this class. */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Injects the user and Jelly avatar images.
     *
     * @param userImage the user's avatar.
     * @param jellyImage Jelly's avatar.
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

        shouldAutoScroll = isNearBottom();
        messageArea.getChildren().add(DialogBox.getUserDialog(input, userImage));
        String response = jelly.executeCommand(input);
        DialogBox responseDialog = DialogBox.getJellyDialog(response, jellyImage);
        if (jelly.wasLastResponseAnError()) {
            responseDialog.getStyleClass().add("error-message");
        }
        messageArea.getChildren().add(responseDialog);
        inputField.clear();

        if (input.equals("bye")) {
            Platform.runLater(stage::close);
            return;
        }

        if (shouldAutoScroll) {
            Platform.runLater(() -> scrollPane.setVvalue(1.0));
        }
    }

    /** Returns whether the conversation is already positioned near its latest message. */
    private boolean isNearBottom() {
        double contentHeight = messageArea.getBoundsInLocal().getHeight();
        double viewportHeight = scrollPane.getViewportBounds().getHeight();
        return contentHeight <= viewportHeight || scrollPane.getVvalue() >= 0.95;
    }
}

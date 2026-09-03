package penguin.gui;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import penguin.Penguin;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    /** Chatbot instance that processes GUI commands. */
    private Penguin penguin;

    /** Image displayed beside user messages. */
    private final Image userImage = loadImage("/images/user.png");

    /** Image displayed beside Penguin messages. */
    private final Image penguinImage = loadImage("/images/chatbot.png");

    /** Binds the dialog container to the scroll pane. */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getPenguinDialog(
                        "Hello! I am Penguin. How can I help you?", penguinImage));
    }

    /**
     * Injects the Penguin chatbot instance.
     *
     * @param penguinInstance chatbot instance used to process commands.
     */
    public void setPenguin(Penguin penguinInstance) {
        assert penguinInstance != null : "Penguin instance must not be null";

        penguin = penguinInstance;
    }

    /**
     * Creates dialog boxes for the input and response, then appends them to.
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert penguin != null : "Penguin must be injected before handling input";

        String input = userInput.getText();

        String response = penguin.getResponse(input);

        if (input.isBlank()) {
            addAnimatedDialog(DialogBox.getPenguinDialog(response, penguinImage));
        } else {
            Node userDialog = DialogBox.getUserDialog(input, userImage);
            Node penguinDialog = DialogBox.getPenguinDialog(response, penguinImage);
            addAnimatedDialog(userDialog);
            addAnimatedDialog(penguinDialog);
        }

        userInput.clear();

        if (penguin.isExitRequested()) {
            Platform.exit();
        }
    }

    /**
     * Loads a required GUI image.
     *
     * @param resourcePath classpath path of the image.
     * @return loaded image.
     * @throws IllegalStateException if the image cannot be found.
     */
    private Image loadImage(String resourcePath) {
        var imageStream = getClass().getResourceAsStream(resourcePath);
        if (imageStream == null) {
            throw new IllegalStateException("Unable to load image: " + resourcePath);
        }
        return new Image(imageStream);
    }

    /**
     * Adds a dialog with a short fade-and-slide entrance animation.
     *
     * @param dialog dialog node to add.
     */
    private void addAnimatedDialog(Node dialog) {
        dialogContainer.getChildren().add(dialog);

        FadeTransition fade = new FadeTransition(Duration.millis(260), dialog);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(
                Duration.millis(260), dialog);
        slide.setFromY(12);
        slide.setToY(0);

        fade.play();
        slide.play();
    }
}

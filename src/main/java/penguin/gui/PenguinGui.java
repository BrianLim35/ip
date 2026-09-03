package penguin.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import penguin.Penguin;

/**
 * A GUI for Penguin using FXML.
 */
public class PenguinGui extends Application {

    /** Chatbot instance used by the GUI. */
    private final Penguin penguin = new Penguin("./data/penguin.txt", false);

    /**
     * Loads and displays the main Penguin window.
     *
     * @param stage primary JavaFX stage.
     * @throws IllegalStateException if the FXML or stylesheet cannot be loaded.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    PenguinGui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            String stylesheet = PenguinGui.class.getResource(
                    "/view/style.css").toExternalForm();
            scene.getStylesheets().add(stylesheet);
            stage.setScene(scene);
            stage.setMinWidth(620);
            stage.setMinHeight(520);
            stage.setWidth(820);
            stage.setHeight(700);
            stage.setTitle("Penguin // AI Assistant");
            fxmlLoader.<MainWindow>getController().setPenguin(penguin);
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load MainWindow.fxml.", e);
        }
    }
}

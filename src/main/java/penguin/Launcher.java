package penguin;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    /**
     * Launches the Penguin JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Application.launch(PenguinGUI.class, args);
    }
}

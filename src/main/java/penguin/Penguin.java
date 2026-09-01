package penguin;

import java.util.ArrayList;

import penguin.command.Command;
import penguin.exception.PenguinException;
import penguin.parser.Parser;
import penguin.storage.Storage;
import penguin.task.TaskList;
import penguin.ui.Ui;

/** Runs the Penguin chatbot. */
public class Penguin {
    /** User interface used for console interaction. */
    private final Ui ui;

    /** Storage used for persistent task data. */
    private final Storage storage;

    /** Task list owned by this chatbot. */
    private final TaskList taskList;

    /** Whether the most recently processed command requested application exit. */
    private boolean isExitRequested;

    /** Creates Penguin using the default storage path. */
    public Penguin() {
        this("./data/penguin.txt");
    }

    /**
     * Creates Penguin using the specified storage path.
     *
     * @param filePath path of the task storage file
     */
    public Penguin(String filePath) {
        this(filePath, true);
    }

    /**
     * Creates Penguin with configurable console output.
     *
     * @param filePath path of the task storage file
     * @param consoleOutputEnabled whether responses should be printed
     */
    public Penguin(String filePath, boolean consoleOutputEnabled) {
        ui = new Ui(consoleOutputEnabled);
        storage = new Storage(filePath);
        taskList = new TaskList();
        loadTasks();
    }

    /** Loads valid saved tasks and reports invalid records individually. */
    private void loadTasks() {
        try {
            ArrayList<String> storageContent = storage.loadTaskLines();

            for (String line : storageContent) {
                try {
                    taskList.addTask(Parser.parseSavedTask(line));
                } catch (PenguinException e) {
                    ui.showError("Skipping invalid saved task! "
                            + e.getMessage());
                }
            }
        } catch (PenguinException e) {
            ui.showError("Unable to load tasks: " + e.getMessage());
        }
    }

    /**
     * Processes one command entered through the GUI.
     *
     * @param input command entered by the user
     * @return complete chatbot response
     */
    public String getResponse(String input) {
        ui.clearResponse();
        isExitRequested = false;

        try {
            if (input == null || input.isBlank()) {
                throw new PenguinException("Please input a task.");
            }

            Command command = Parser.parseCommand(input);
            command.execute(taskList, ui, storage);
            isExitRequested = command.isExit();
        } catch (PenguinException e) {
            ui.showError(e.getMessage());
        }

        return ui.getResponse();
    }

    /**
     * Checks whether the most recently processed command requested application exit.
     *
     * @return true if the application should exit
     */
    public boolean isExitRequested() {
        return isExitRequested;
    }

    /** Runs the chatbot command loop. */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String fullCommand = ui.readCommand();

            if (fullCommand == null) {
                break;
            }

            ui.showDivider();

            try {
                if (fullCommand.isBlank()) {
                    throw new PenguinException("Please input a task.");
                }

                Command command = Parser.parseCommand(fullCommand);
                command.execute(taskList, ui, storage);
                isExit = command.isExit();
            } catch (PenguinException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showDivider();
            }
        }
    }

    /**
     * Starts Penguin with its default storage path.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        new Penguin().run();
    }
}

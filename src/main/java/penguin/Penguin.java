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
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList();
        loadTasks();
    }

    /** Loads valid saved tasks and reports invalid records individually. */
    private void loadTasks() {
        try {
            ArrayList<String> storageContent = storage.read();

            for (String line : storageContent) {
                try {
                    taskList.addTask(Parser.parseTask(line));
                } catch (PenguinException e) {
                    ui.showError("Skipping invalid saved task! "
                            + e.getMessage());
                }
            }
        } catch (PenguinException e) {
            ui.showError("Unable to load tasks: " + e.getMessage());
        }
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

            ui.showLine();

            try {
                if (fullCommand.isBlank()) {
                    throw new PenguinException("Please input a task.");
                }

                Command command = Parser.parse(fullCommand);
                command.execute(taskList, ui, storage);
                isExit = command.isExit();
            } catch (PenguinException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
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

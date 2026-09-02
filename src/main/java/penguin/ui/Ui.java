package penguin.ui;

import penguin.task.Task;
import penguin.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/** Handles console interaction for Penguin. */
public class Ui {
    /** Separator printed between chatbot messages. */
    private static final String LINE = "----------------------------------------------------------";

    /** ASCII-art banner displayed when the chatbot starts. */
    private static final String BANNER = " ____  _____ _   _  ____ _   _ ___ _   _ \n"
            + "|  _ \\| ____| \\ | |/ ___| | | |_ _| \\ | |\n"
            + "| |_) |  _| |  \\| | |  _| | | || ||  \\| |\n"
            + "|  __/| |___| |\\  | |_| | |_| || || |\\  |\n"
            + "|_|   |_____|_| \\_|\\____|\\___/|___|_| \\_|";

    /** Greeting displayed when the chatbot starts. */
    private static final String GREETING_MESSAGE =
            "Hello! I'm Penguin.\nWhat can I do for you?";

    /** Farewell displayed when the chatbot exits. */
    private static final String GOODBYE_MESSAGE =
            "Bye. Hope to see you again soon!";

    /** Scanner used to read user commands. */
    private final Scanner scanner;

    /** Stores the latest response for non-console clients such as the GUI. */
    private final StringBuilder response = new StringBuilder();

    /** Whether responses should also be printed to standard output. */
    private final boolean consoleOutputEnabled;

    /** Creates a user interface using standard input. */
    public Ui() {
        this(true);
    }

    /**
     * Creates a user interface with configurable console output.
     *
     * @param enableConsoleOutput whether responses should be printed
     */
    public Ui(boolean enableConsoleOutput) {
        scanner = new Scanner(System.in);
        this.consoleOutputEnabled = enableConsoleOutput;
    }

    /** Displays the welcome message. */
    public void showWelcome() {
        System.out.printf("%s%n", LINE);
        System.out.printf("%s%n", BANNER);
        System.out.printf("%s%n", LINE);
        System.out.printf("%s%n", GREETING_MESSAGE);
        System.out.printf("%s%n", LINE);
    }

    /**
     * Reads one command from the user.
     *
     * @return trimmed user command, or {@code null} at end of input
     */
    public String readCommand() {
        System.out.printf("%s", "You: ");
        return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
    }

    /** Displays the message divider. */
    public void showDivider() {
        System.out.printf("%s%n", LINE);
    }

    /**
     * Displays a normal chatbot message.
     *
     * @param message message to display
     */
    public void showMessage(String message) {
        record("Penguin: " + message);
    }

    /**
     * Displays an error message.
     *
     * @param message error message to display
     */
    public void showError(String message) {
        showMessage(message);
    }

    /** Displays the farewell message. */
    public void showGoodbye() {
        record(GOODBYE_MESSAGE);
    }

    /**
     * Stores a response and optionally prints it to the console.
     *
     * @param message response to store and display
     */
    private void record(String message) {
        response.append(message).append(System.lineSeparator());
        if (consoleOutputEnabled) {
            System.out.printf("%s%n", message);
        }
    }

    /** Clears the response buffer. */
    public void clearResponse() {
        response.setLength(0);
    }

    /**
     * Gets the latest buffered response.
     *
     * @return latest response without trailing whitespace
     */
    public String getResponse() {
        return response.toString().trim();
    }

    /**
     * Displays all tasks or the empty-list message.
     *
     * @param taskList task list to display
     */
    public void showTasks(TaskList taskList) {
        if (taskList.isEmpty()) {
            showMessage("Your task list is empty!");
            return;
        }

        showMessage("Here are your tasks!");
        showTaskLines(taskList.getTasks());
    }

    /**
     * Displays tasks occurring on a date.
     *
     * @param date date being displayed
     * @param tasks matching tasks
     */
    public void showTasksOnDate(LocalDate date, ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            showMessage(String.format("No deadlines or events occur on %s.",
                    formatDate(date)));
            return;
        }

        showMessage(String.format("Here are your tasks on %s!", formatDate(date)));
        showTaskLines(tasks);
    }

    /** Formats a date using the display format shown to users. */
    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH));
    }

    /**
     * Displays tasks matching a keyword or a no-match message.
     *
     * @param keyword keyword or phrase used for the search
     * @param tasks matching tasks to display
     */
    public void showMatchingTasks(String keyword, ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            showMessage(String.format("No tasks found when searching for %s.",
                    keyword));
            return;
        }

        showMessage(String.format("Here are the matching tasks containing %s in your list:",
                keyword));
        showTaskLines(tasks);
    }

    /**
     * Displays numbered task lines.
     *
     * @param tasks tasks to display
     */
    private void showTaskLines(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            record((i + 1) + ". " + tasks.get(i));
        }
    }
}

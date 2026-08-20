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

    /** Creates a user interface using standard input. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /** Displays the welcome message. */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println(LINE);
        System.out.println(GREETING_MESSAGE);
        System.out.println(LINE);
    }

    /**
     * Reads one command from the user.
     *
     * @return trimmed user command, or {@code null} at end of input
     */
    public String readCommand() {
        System.out.print("You: ");
        return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
    }

    /** Displays the message divider. */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays a normal chatbot message.
     *
     * @param message message to display
     */
    public void showMessage(String message) {
        System.out.println("Penguin: " + message);
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
        System.out.println(GOODBYE_MESSAGE);
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
    public void showTasksOn(LocalDate date, ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            showMessage(String.format("No deadlines or events occur on %s.",
                    date.format(DateTimeFormatter.ofPattern(
                            "d MMM yyyy", Locale.ENGLISH))));
            return;
        }

        showMessage(String.format("Here are your tasks on %s!",
                date.format(DateTimeFormatter.ofPattern(
                        "d MMM yyyy", Locale.ENGLISH))));
        showTaskLines(tasks);
    }

    /**
     * Displays numbered task lines.
     *
     * @param tasks tasks to display
     */
    private void showTaskLines(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}

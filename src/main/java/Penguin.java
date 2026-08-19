import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs the Penguin chatbot.
 */
public class Penguin {
    /** Delimiter reserved for the saved task-file format. */
    private static final String STORAGE_DELIMITER = "|";
    /** Separator printed between chatbot messages. */
    private static final String LINE = "----------------------------------------------------------";
    /** ASCII-art banner displayed when the chatbot starts. */
    private static final String BANNER = " ____  _____ _   _  ____ _   _ ___ _   _ \n"
            + "|  _ \\| ____| \\ | |/ ___| | | |_ _| \\ | |\n"
            + "| |_) |  _| |  \\| | |  _| | | || ||  \\| |\n"
            + "|  __/| |___| |\\  | |_| | |_| || || |\\  |\n"
            + "|_|   |_____|_| \\_|\\____|\\___/|___|_| \\_|";
    /** Greeting displayed when the chatbot starts. */
    private static final String GREETING_MESSAGE = "Hello! I'm Penguin.\nWhat can I do for you?";
    /** Farewell displayed when the chatbot exits. */
    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you again soon!";

    /** Creates a Penguin chatbot. */
    public Penguin() {
    }

    /**
     * Prints the chatbot banner and greeting.
     */
    private static void printIntroduction() {
        // Chatbot banner
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println(LINE);

        // Chatbot greetings
        System.out.println(GREETING_MESSAGE);
        System.out.println(LINE);
    }

    /**
     * Converts the task number entered by the user into zero-based index.
     *
     * @param command user command containing the task number
     * @return zero-based task index
     * @throws PenguinException if the command does not contain a valid task number
     */
    private static int getIndex(String command) throws PenguinException {
        String[] parts = command.split("\\s+");

        if (parts.length != 2) {
            throw new PenguinException("Please enter a task number.");
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new PenguinException("Please enter a valid task number.");
        }
    }

    /**
     * Identifies the command represented by user input.
     *
     * @param input complete user input
     * @return the corresponding command type
     * @throws PenguinException if the input does not start with a known command
     */
    private static CommandType getCommandType(String input) throws PenguinException {
        String trimmedInput = input.trim();
        String firstWord = trimmedInput.split("\\s+", 2)[0].toLowerCase();

        if (firstWord.equals("bye") && !trimmedInput.equalsIgnoreCase("bye")) {
            throw new PenguinException("The bye command does not take arguments.");
        }

        return switch (firstWord) {
        case "todo" -> CommandType.TODO;
        case "deadline" -> CommandType.DEADLINE;
        case "event" -> CommandType.EVENT;
        case "list" -> {
            if (!trimmedInput.equalsIgnoreCase("list")) {
                throw new PenguinException("The list command does not take arguments.");
            }
            yield CommandType.LIST;
        }
        case "mark" -> CommandType.MARK;
        case "unmark" -> CommandType.UNMARK;
        case "delete" -> CommandType.DELETE;
        case "bye" -> CommandType.BYE;
        default -> throw new PenguinException("I don't understand that command.");
        };
    }

    /**
     * Converts a task-creation command into the appropriate task subtype.
     *
     * @param command complete task-creation command entered by the user
     * @return a task created from the command's description and date/time values
     * @throws PenguinException if the command is unknown or has invalid arguments
     */
    private static Task createTask(String command) throws PenguinException {
        String lowerCaseCommand = command.toLowerCase();

        if (lowerCaseCommand.equals("todo") || lowerCaseCommand.startsWith("todo ")) {
            String description = command.length() <= 4 ? "" : command.substring(4).trim();
            if (description.isEmpty()) {
                throw new PenguinException("The description of a todo cannot be empty.");
            }
            validateDescription(description);
            return new ToDo(description);
        }

        if (lowerCaseCommand.equals("deadline") || lowerCaseCommand.startsWith("deadline ")) {
            String content = command.length() <= 9 ? "" : command.substring(9);
            String separator = " /by";
            String lowerCaseContent = content.toLowerCase();
            if (content.trim().isEmpty() || lowerCaseContent.trim().equals("/by")
                    || lowerCaseContent.trim().startsWith("/by ")) {
                throw new PenguinException("The description of a deadline cannot be empty.");
            }
            if (!lowerCaseContent.contains(separator)) {
                throw new PenguinException("A deadline must contain /by followed by a date or time.");
            }
            if (lowerCaseContent.indexOf(separator) != lowerCaseContent.lastIndexOf(separator)) {
                throw new PenguinException("A deadline must contain only one /by separator.");
            }
            int separatorIndex = lowerCaseContent.indexOf(separator);
            String description = content.substring(0, separatorIndex).trim();
            String dateTime = content.substring(separatorIndex + separator.length()).trim();
            if (description.isEmpty()) {
                throw new PenguinException("A deadline must have a description before /by.");
            }
            validateDescription(description);
            if (dateTime.isEmpty()) {
                throw new PenguinException("A deadline must have a date or time after /by.");
            }
            return new Deadline(description, dateTime);
        }

        if (lowerCaseCommand.equals("event") || lowerCaseCommand.startsWith("event ")) {
            String content = command.length() <= 6 ? "" : command.substring(6);
            String fromSeparator = " /from";
            String toSeparator = " /to";
            String lowerCaseContent = content.toLowerCase();
            if (lowerCaseContent.startsWith("/from ") || lowerCaseContent.startsWith("/to ")) {
                throw new PenguinException("The description of an event cannot be empty.");
            }
            int fromIndex = lowerCaseContent.indexOf(fromSeparator);
            int toIndex = lowerCaseContent.indexOf(toSeparator);
            if (fromIndex < 0 || toIndex < 0 || fromIndex > toIndex
                    || fromIndex != lowerCaseContent.lastIndexOf(fromSeparator)
                    || toIndex != lowerCaseContent.lastIndexOf(toSeparator)) {
                throw new PenguinException("An event must contain one /from and one /to separator.");
            }
            String description = content.substring(0, fromIndex).trim();
            String from = content.substring(fromIndex + fromSeparator.length(), toIndex).trim();
            String to = content.substring(toIndex + toSeparator.length()).trim();
            if (description.isEmpty()) {
                throw new PenguinException("The description of an event cannot be empty.");
            }
            validateDescription(description);
            if (from.isEmpty() || to.isEmpty()) {
                throw new PenguinException("An event must have both a start and an end time.");
            }
            return new Event(description, from, to);
        }

        throw new PenguinException("I don't understand that command.");
    }

    /**
     * Rejects characters that would make a saved task ambiguous.
     *
     * @param description task description to validate
     * @throws PenguinException if the description contains the storage delimiter
     */
    private static void validateDescription(String description) throws PenguinException {
        if (description.contains(STORAGE_DELIMITER)) {
            throw new PenguinException("Task descriptions cannot contain the | character.");
        }
    }

    /**
     * Reads and processes commands until the user enters "bye" or input ends.
     *
     * @param scanner used to read user commands
     * @param taskList stores the user's tasks
     * @param storage handles saving and loading task data
     */
    private static void runCommandLoop(Scanner scanner, TaskList taskList, Storage storage) {
        while (true) {
            System.out.print("You: ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String command = scanner.nextLine().trim();

            // Ignores empty inputs for command
            if (command.isBlank()) {
                System.out.println("Penguin: Please input a task.");
                System.out.println(LINE);
                continue;
            }

            try {
                CommandType commandType = getCommandType(command);
                switch (commandType) {
                case BYE:
                    System.out.println(LINE);
                    System.out.println(GOODBYE_MESSAGE);
                    System.out.println(LINE);
                    return;
                case LIST:
                    if (taskList.isEmpty()) {
                        System.out.println("Penguin: Your task list is empty!");
                    } else {
                        System.out.println("Penguin: Here are your tasks!");
                        taskList.listTasks();
                    }
                    break;
                case MARK:
                    try {
                        int index = getIndex(command);
                        Task task = taskList.markTask(index);
                        storage.save(taskList.toFileLines());
                        System.out.println("Penguin: The following task has been marked.\n" + task);
                    } catch (PenguinException e) {
                        System.out.println("Penguin: " + e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Penguin: Invalid task index!");
                    }
                    break;
                case UNMARK:
                    try {
                        int index = getIndex(command);
                        Task task = taskList.unmarkTask(index);
                        storage.save(taskList.toFileLines());
                        System.out.println("Penguin: The following task has been unmarked.\n" + task);
                    } catch (PenguinException e) {
                        System.out.println("Penguin: " + e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Penguin: Invalid task index!");
                    }
                    break;
                case DELETE:
                    try {
                        int index = getIndex(command);
                        Task task = taskList.deleteTask(index);
                        storage.save(taskList.toFileLines());
                        System.out.println("Penguin: I have removed '" + task + "' from your list of tasks."
                                + " Now you have " + taskList.size() + " task(s) in the list.");
                    } catch (PenguinException e) {
                        System.out.println("Penguin: " + e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Penguin: Invalid task index!");
                    }
                    break;
                case TODO:
                case DEADLINE:
                case EVENT:
                    try {
                        Task task = createTask(command);
                        taskList.addTask(task);
                        storage.save(taskList.toFileLines());
                        System.out.println("Penguin: I have added '" + task + "' to your list of tasks."
                                + " Now you have " + taskList.size() + " task(s) in the list.");
                    } catch (PenguinException e) {
                        System.out.println("Penguin: " + e.getMessage());
                    }
                    break;
                default:
                    throw new PenguinException("I don't understand that command.");
                }
            } catch (PenguinException e) {
                System.out.println("Penguin: " + e.getMessage());
            }

            System.out.println(LINE);
        }
    }

    /**
     * Starts the chatbot and runs its command loop.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        // Print chatbot banner and greetings
        printIntroduction();

        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();
        Storage storage = new Storage("./data/penguin.txt");

        try {
            ArrayList<String> storageContent = storage.read();

            for (String line : storageContent) {
                try {
                    taskList.addTask(Parser.parseTask(line));
                } catch (PenguinException e) {
                    System.out.println("Penguin: Skipping invalid saved task! "
                            + e.getMessage());
                }
            }
        } catch (PenguinException e) {
            System.out.println("Penguin: Unable to load tasks: " + e.getMessage());
        }

        // Perform commands entered by user, and exit when user enters "bye"
        runCommandLoop(scanner, taskList, storage);

        scanner.close();
    }
}

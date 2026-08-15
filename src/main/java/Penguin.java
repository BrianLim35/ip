import java.util.Scanner;

/**
 * Runs the Penguin chatbot.
 */
public class Penguin {
    private static final String LINE = "----------------------------------------------------------";
    private static final String BANNER = " ____  _____ _   _  ____ _   _ ___ _   _ \n"
            + "|  _ \\| ____| \\ | |/ ___| | | |_ _| \\ | |\n"
            + "| |_) |  _| |  \\| | |  _| | | || ||  \\| |\n"
            + "|  __/| |___| |\\  | |_| | |_| || || |\\  |\n"
            + "|_|   |_____|_| \\_|\\____|\\___/|___|_| \\_|";
    private static final String GREETING_MESSAGE = "Hello! I'm Penguin.\nWhat can I do for you?";
    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you again soon!";

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
     * @throws IllegalArgumentException if the command does not contain a task number
     * @throws NumberFormatException if the task number is not an integer
     */
    private static int getIndex(String command) {
        String[] parts = command.split("\\s+");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Please enter a task number.");
        }

        return Integer.parseInt(parts[1]) - 1;
    }

    /**
     * Reads and echoes commands until the user enters "bye" or input ends.
     *
     * @param scanner used to read user commands
     * @param taskList stores the user's tasks
     */
    private static void runCommandLoop(Scanner scanner, TaskList taskList) {
        while (true) {
            System.out.print("You: ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String command = scanner.nextLine().trim();

            // Chatbot exits when user enters "bye"
            if (command.equalsIgnoreCase("bye")) {
                System.out.println(LINE);
                System.out.println(GOODBYE_MESSAGE);
                System.out.println(LINE);
                break;
            }

            // Ignores empty inputs for command
            if (command.isBlank()) {
                System.out.println("Penguin: Please input a task.");
                System.out.println(LINE);
                continue;
            }

            // Commands that user can perform: list, mark, unmark, add task to list
            if (command.equalsIgnoreCase("list")) {
                if (taskList.isEmpty()) {
                    System.out.println("Penguin: Your task list is empty!");
                } else {
                    System.out.println("Penguin: Here are your tasks!");
                    for (int i = 0; i < taskList.size(); i++) {
                        System.out.println((i + 1) + ". " +
                                "[" + taskList.getTask(i).getStatus() + "] " +
                                taskList.getTask(i));
                    }
                }
            } else if (command.toLowerCase().startsWith("mark ")) {
                try {
                    int index = getIndex(command);
                    Task task = taskList.getTask(index);
                    task.markDone();
                    System.out.println("Penguin: The following task has been marked.\n" +
                            "[" + task.getStatus() + "] " +
                            task);
                } catch (NumberFormatException e) {
                    System.out.println("Penguin: Please enter a valid task number.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Penguin: Invalid task index!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Penguin: " + e.getMessage());
                }
            } else if (command.toLowerCase().startsWith("unmark ")) {
                try {
                    int index = getIndex(command);
                    Task task = taskList.getTask(index);
                    task.markUndone();
                    System.out.println("Penguin: The following task has been unmarked.\n" +
                            "[" + task.getStatus() + "] " +
                            task);
                } catch (NumberFormatException e) {
                    System.out.println("Penguin: Please enter a valid task number.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Penguin: Invalid task index!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Penguin: " + e.getMessage());
                }
            } else {
                Task task = new Task(command);
                taskList.addTask(task);
                System.out.println("Penguin: I have added '" + task + "' to your list of tasks!");
            }

            System.out.println(LINE);
        }
    }

    public static void main(String[] args) {
        // Print chatbot banner and greetings
        printIntroduction();

        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();

        // Perform commands entered by user, and exit when user enters "bye"
        runCommandLoop(scanner, taskList);

        scanner.close();
    }
}

import java.util.Scanner;

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
     * Prints the chatbot banner and greeting
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
     * Reads and echoes commands until the user enters "bye" or input ends
     *
     * @param scanner used to read user commands
     */
    private static void runCommandLoop(Scanner scanner) {
        while (true) {
            System.out.print("You: ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String command = scanner.nextLine();

            // Chatbot exits when user types "bye"
            if (command.equalsIgnoreCase("bye")) {
                System.out.println(LINE);
                System.out.println(GOODBYE_MESSAGE);
                System.out.println(LINE);
                break;
            }

            // Ignores empty inputs for command
            if (command.isBlank()) {
                continue;
            }

            // Chatbot continues the loop to prompt for user command
            System.out.println("Penguin: " + command);
            System.out.println(LINE);
        }
    }

    public static void main(String[] args) {
        // Print chatbot banner and greetings
        printIntroduction();

        // Chatbot echo: Echo commands entered by user, and exit when user types "bye"
        Scanner scanner = new Scanner(System.in);
        runCommandLoop(scanner);
        scanner.close();
    }
}

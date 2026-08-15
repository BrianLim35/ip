public class Penguin {
    private static final String LINE = "----------------------------------------------------------";
    private static final String BANNER = " ____  _____ _   _  ____ _   _ ___ _   _ \n"
            + "|  _ \\| ____| \\ | |/ ___| | | |_ _| \\ | |\n"
            + "| |_) |  _| |  \\| | |  _| | | || ||  \\| |\n"
            + "|  __/| |___| |\\  | |_| | |_| || || |\\  |\n"
            + "|_|   |_____|_| \\_|\\____|\\___/|___|_| \\_|";
    private static final String GREETING_MESSAGE = "Hello! I'm Penguin.\nWhat can I do for you?";
    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you again soon!";

    public static void main(String[] args) {
        // Chatbot banner
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println(LINE);

        // Chatbot greetings
        System.out.println(GREETING_MESSAGE);
        System.out.println(LINE);

        // Chatbot exits
        System.out.println(GOODBYE_MESSAGE);
        System.out.println(LINE);
    }
}

/** Defines the commands understood by the Penguin chatbot. */
public enum CommandType {
    /** Creates a to-do task. */
    TODO,
    /** Creates a deadline task. */
    DEADLINE,
    /** Creates an event task. */
    EVENT,
    /** Displays all tasks. */
    LIST,
    /** Marks a task as completed. */
    MARK,
    /** Marks a task as incomplete. */
    UNMARK,
    /** Deletes a task. */
    DELETE,
    /** Exits the chatbot. */
    BYE
}

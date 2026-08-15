/** Represents a task without an attached date or time. */
public class ToDo extends Task {
    /**
     * Creates a to-do task without an attached date or time.
     *
     * @param description description of the task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the to-do task formatted for display.
     *
     * @return formatted to-do description and completion status
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

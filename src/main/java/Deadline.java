/** Represents a task that must be completed by a specified date or time. */
public class Deadline extends Task {
    /** Date or time by which the task should be completed. */
    private final String dateTime;

    /**
     * Creates a deadline with a description and due date/time.
     *
     * @param description description of the task
     * @param dateTime date or time by which the task should be completed
     */
    public Deadline(String description, String dateTime) {
        super(description);
        this.dateTime = dateTime;
    }

    /**
     * Returns the deadline formatted for display.
     *
     * @return formatted deadline description and due date/time
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dateTime + ")";
    }
}

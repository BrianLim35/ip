import java.time.LocalDate;
import java.time.LocalDateTime;

/** Represents a task that must be completed by a specified date or time. */
public class Deadline extends Task {
    /** Date or time by which the task should be completed. */
    private final LocalDateTime dateTime;

    /**
     * Creates a deadline with a description and due date/time.
     *
     * @param description description of the task
     * @param dateTime date or time by which the task should be completed
     */
    public Deadline(String description, LocalDateTime dateTime) {
        super(description, TaskType.DEADLINE);
        this.dateTime = dateTime;
    }

    @Override
    public String toFileFormat() {
        return String.format("%s | %s", super.toFileFormat(),
                DateTimeUtil.formatForStorage(dateTime));
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return this.dateTime.toLocalDate().equals(date);
    }

    /**
     * Returns the deadline formatted for display.
     *
     * @return formatted deadline description and due date/time
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeUtil.format(dateTime) + ")";
    }
}

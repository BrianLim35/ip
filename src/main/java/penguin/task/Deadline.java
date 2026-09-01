package penguin.task;

import penguin.enums.TaskType;
import penguin.util.DateTimeUtil;

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
     * @param deadlineDateTime date or time by which the task should be completed
     */
    public Deadline(String description, LocalDateTime deadlineDateTime) {
        super(description, TaskType.DEADLINE);
        this.dateTime = deadlineDateTime;
    }

    /**
     * Returns the deadline in persistent storage format.
     *
     * @return serialized deadline data
     */
    @Override
    public String toStorageFormat() {
        return String.format("%s | %s", super.toStorageFormat(),
                DateTimeUtil.formatForStorage(dateTime));
    }

    /**
     * Checks whether the deadline occurs on a specified date.
     *
     * @param date date to check
     * @return true if the deadline is on the specified date
     */
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
        return super.toString() + " (by: " + DateTimeUtil.formatForDisplay(dateTime) + ")";
    }
}

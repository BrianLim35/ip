import java.time.LocalDate;
import java.time.LocalDateTime;

/** Represents a task that starts and ends at specified date or time values. */
public class Event extends Task {
    /** Start date or time of the event. */
    private final LocalDateTime from;
    /** End date or time of the event. */
    private final LocalDateTime to;

    /**
     * Creates an event with a description, start time, and end time.
     *
     * @param description description of the event
     * @param from date or time when the event starts
     * @param to date or time when the event ends
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toFileFormat() {
        return String.format("%s | %s | %s", super.toFileFormat(),
                DateTimeUtil.formatForStorage(from),
                DateTimeUtil.formatForStorage(to));
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return !date.isBefore(from.toLocalDate()) && !date.isAfter(to.toLocalDate());
    }

    /**
     * Returns the event formatted for display.
     *
     * @return formatted event description and date/time range
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " +
                DateTimeUtil.format(from) + " to: " +
                DateTimeUtil.format(to) + ")";
    }
}

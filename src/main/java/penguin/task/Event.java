package penguin.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import penguin.enums.TaskType;
import penguin.util.DateTimeUtil;

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
     * @param startDateTime date or time when the event starts
     * @param endDateTime date or time when the event ends
     */
    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(description, TaskType.EVENT);
        this.from = startDateTime;
        this.to = endDateTime;
    }

    /** Creates an independent copy of this event. */
    @Override
    public Task copy() {
        Event copy = new Event(getDescription(), from, to);
        if ("X".equals(getStatus())) {
            copy.markDone();
        }
        return copy;
    }

    /**
     * Returns the event in persistent storage format.
     *
     * @return serialized event data
     */
    @Override
    public String toStorageFormat() {
        return String.format("%s | %s | %s", super.toStorageFormat(),
                DateTimeUtil.formatForStorage(from),
                DateTimeUtil.formatForStorage(to));
    }

    /**
     * Checks whether the event spans the specified date.
     *
     * @param date date to check
     * @return true if the event occurs on the specified date
     */
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
        return super.toString() + " (from: "
                + DateTimeUtil.formatForDisplay(from) + " to: "
                + DateTimeUtil.formatForDisplay(to) + ")";
    }
}

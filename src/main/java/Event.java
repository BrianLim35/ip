/** Represents a task that starts and ends at specified date or time values. */
public class Event extends Task {
    /** Start date or time of the event. */
    private final String from;
    /** End date or time of the event. */
    private final String to;

    /**
     * Creates an event with a description, start time, and end time.
     *
     * @param description description of the event
     * @param from date or time when the event starts
     * @param to date or time when the event ends
     */
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toFileFormat() {
        return String.format("%s | %s | %s", super.toFileFormat(), from, to);
    }

    /**
     * Returns the event formatted for display.
     *
     * @return formatted event description and date/time range
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

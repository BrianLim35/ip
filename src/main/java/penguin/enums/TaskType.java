package penguin.enums;

/** Defines the supported task types and their display symbols. */
public enum TaskType {
    /** A task without an attached date or time. */
    TODO("T"),
    /** A task that must be completed by a date or time. */
    DEADLINE("D"),
    /** A task that occurs between a start and end date or time. */
    EVENT("E");

    /** Symbol used when displaying this task type. */
    private final String symbol;

    /**
     * Creates a task type with its display symbol.
     *
     * @param displaySymbol symbol used when displaying the task type
     */
    TaskType(String displaySymbol) {
        this.symbol = displaySymbol;
    }

    /**
     * Returns the display symbol for this task type.
     *
     * @return the task type symbol
     */
    public String getSymbol() {
        return symbol;
    }
}

/**
 * Represents a task that can be marked as completed or incomplete.
 */
public class Task {
    /** The description of this task. */
    private final String description;

    /** Whether this task has been completed. */
    private boolean isDone;

    /** Type of this task. */
    private final TaskType type;

    /**
     * Creates an incomplete task of the specified type.
     *
     * @param description the description of the task
     * @param type the type of the task
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Returns the display marker for this task's completion status.
     *
     * @return {@code "X"} if the task is done, or a space otherwise
     */
    public String getStatus() {
        return isDone ? "X" : " ";
    }

    /** Marks this task as completed. */
    public void markDone() {
        isDone = true;
    }

    /** Marks this task as incomplete. */
    public void markUndone() {
        isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return the task status and description formatted for display
     */
    @Override
    public String toString() {
        return "[" + type.getSymbol() + "][" + getStatus() + "] " + description;
    }
}

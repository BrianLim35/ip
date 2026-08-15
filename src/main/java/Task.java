/**
 * Represents a task that can be marked as completed or incomplete.
 */
public class Task {
    /** The description of this task. */
    private final String description;

    /** Whether this task has been completed. */
    private boolean isDone;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
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
     * @return the task description
     */
    @Override
    public String toString() {
        return description;
    }
}

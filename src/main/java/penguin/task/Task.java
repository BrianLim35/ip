package penguin.task;

import java.time.LocalDate;
import java.util.Objects;

import penguin.enums.TaskType;

/**
 * Represents a task that can be marked as completed or incomplete.
 */
public class Task {
    /** Delimiter reserved by the persistent storage format. */
    public static final String STORAGE_DELIMITER = "|";

    /** The description of this task. */
    private final String description;

    /** Whether this task has been completed. */
    private boolean isDone;

    /** Type of this task. */
    private final TaskType type;

    /**
     * Creates an incomplete task of the specified type.
     *
     * @param taskDescription the description of the task
     * @param taskType the type of the task
     */
    public Task(String taskDescription, TaskType taskType) {
        this.description = Objects.requireNonNull(taskDescription, "Description must not be null");
        if (taskDescription.isBlank()) {
            throw new IllegalArgumentException("Description must not be blank");
        }
        this.isDone = false;
        this.type = Objects.requireNonNull(taskType, "Task type must not be null");
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
     * Creates an independent copy of this task.
     *
     * @return copy with the same completion status
     */
    public Task copy() {
        Task copy = new Task(description, type);
        if (isDone) {
            copy.markDone();
        }
        return copy;
    }

    /**
     * Returns this task's description for subclass copy operations.
     *
     * @return this task's description
     */
    protected String getDescription() {
        return description;
    }

    /**
     * Returns this task in the format used for persistent storage.
     *
     * @return task type, completion status, and description separated by pipes
     */
    public String toStorageFormat() {
        String status = isDone ? "1" : "0";
        return String.format("%s | %s | %s", type.getSymbol(), status, description);
    }

    /**
     * Checks whether this task occurs on the specified date.
     *
     * @param date date to check
     * @return false because a normal to-do has no date
     */
    public boolean occursOn(LocalDate date) {
        return false;
    }

    /**
     * Checks whether this task description contains the specified keyword.
     *
     * @param keyword keyword or phrase to search for
     * @return true if the description contains the keyword
     */
    public boolean containsKeyword(String keyword) {
        return description.contains(keyword);
    }

    /**
     * Returns the task status, type, and description formatted for display.
     *
     * @return the task status, type, and description formatted for display
     */
    @Override
    public String toString() {
        return "[" + type.getSymbol() + "][" + getStatus() + "] " + description;
    }
}

package penguin.task;

import penguin.enums.TaskType;

/** Represents a task without an attached date or time. */
public class Todo extends Task {

    /**
     * Creates a to-do task without an attached date or time.
     *
     * @param description description of the task.
     * @throws NullPointerException if the description is null.
     * @throws IllegalArgumentException if the description is blank or contains
     *                                  the storage delimiter.
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    /**
     * Creates an independent copy of this to-do.
     *
     * @return copy with the same description and completion status.
     */
    @Override
    public Task copy() {
        Todo copy = new Todo(getDescription());
        copyCompletionStatusTo(copy);
        return copy;
    }
}

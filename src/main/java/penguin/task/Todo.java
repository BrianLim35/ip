package penguin.task;

import penguin.enums.TaskType;

/** Represents a task without an attached date or time. */
public class Todo extends Task {

    /**
     * Creates a to-do task without an attached date or time.
     *
     * @param description description of the task.
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    /** Creates an independent copy of this to-do. */
    @Override
    public Task copy() {
        Todo copy = new Todo(getDescription());
        if ("X".equals(getStatus())) {
            copy.markDone();
        }
        return copy;
    }
}

package penguin.task;

import penguin.enums.TaskType;

/** Represents a task without an attached date or time. */
public class ToDo extends Task {

    /**
     * Creates a to-do task without an attached date or time.
     *
     * @param description description of the task
     */
    public ToDo(String description) {
        super(description, TaskType.TODO);
    }

    /** Creates an independent copy of this to-do. */
    @Override
    public Task copy() {
        ToDo copy = new ToDo(getDescription());
        if ("X".equals(getStatus())) {
            copy.markDone();
        }
        return copy;
    }
}

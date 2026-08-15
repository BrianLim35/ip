import java.util.ArrayList;

/**
 * Stores the tasks entered by the user.
 */
public class TaskList {
    // The tasks currently stored in the list.
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the end of the task list.
     *
     * @param task the task description to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieves a task based on its zero-based index.
     *
     * @param index zero-based index of the task to retrieve
     * @return the task at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of stored tasks.
     *
     * @return number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks whether the task list is empty.
     *
     * @return true if the task list is empty, otherwise false
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}

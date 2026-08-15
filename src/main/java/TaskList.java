import java.util.ArrayList;

/**
 * Stores the tasks entered by the user.
 */
public class TaskList {
    // The tasks currently stored in the list
    private final ArrayList<String> tasks = new ArrayList<>();

    /**
     * Adds a task to the end of the task list.
     *
     * @param task the task description to add
     */
    public void addTask(String task) {
        tasks.add(task);
    }

    /**
     * Retrieve the task based on its index.
     *
     * @param index index of the task to be retrieved
     * @return task description
     */
    public String getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task index: " + index);
        }

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

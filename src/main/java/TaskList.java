import java.util.ArrayList;

/**
 * Stores the tasks entered by the user.
 */
public class TaskList {
    /** The tasks currently stored in the list. */
    private final ArrayList<Task> tasks = new ArrayList<>();

    /** Creates an empty task list. */
    public TaskList() {
    }

    /**
     * Adds a task to the end of the task list.
     *
     * @param task the task description to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param index zero-based index of the task to mark
     * @return the task after it has been marked as completed
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task markTask(int index) {
        Task task = tasks.get(index);
        task.markDone();
        return task;
    }

    /**
     * Marks the task at the specified index as incomplete.
     *
     * @param index zero-based index of the task to unmark
     * @return the task after it has been marked as incomplete
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task unmarkTask(int index) {
        Task task = tasks.get(index);
        task.markUndone();
        return task;
    }

    /** Displays all tasks in their insertion order. */
    public void listTasks() {
        for (int i = 0; i < size(); i++) {
            System.out.println((i + 1) + ". " +
                    tasks.get(i));
        }
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

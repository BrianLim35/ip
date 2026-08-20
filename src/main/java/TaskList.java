import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Stores the tasks entered by the user.
 */
public class TaskList {
    /** The tasks currently stored in the list. */
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the end of the task list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at the specified index.
     *
     * @param index zero-based index of the task to delete
     * @return the deleted task
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
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

    /**
     * Returns all tasks converted into lines for persistent storage.
     *
     * @return task data formatted as storage lines
     */
    public ArrayList<String> toFileLines() {
        ArrayList<String> lines = new ArrayList<>();

        for (Task task : tasks) {
            lines.add(task.toFileFormat());
        }

        return lines;
    }

    public ArrayList<Task> getTasksOn(LocalDate date) {
        ArrayList<Task> tasksOn = new ArrayList<>();
        for  (Task task : tasks) {
            if (task.occursOn(date)) {
                tasksOn.add(task);
            }
        }
        return tasksOn;
    }
}

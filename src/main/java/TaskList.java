import java.util.ArrayList;

/**
 * Stores the tasks entered by the user.
 */
public class TaskList {
    /** The tasks currently stored in the list. */
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the end of the task list.
     *
     * @param task the task description to add
     */
    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Penguin: I have added '" + task + "' to your list of tasks!");
    }

    /** Marks the task at the specified index as completed and displays it.
     *
     * @param index zero-based index of the task to mark
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void markTask(int index) {
        tasks.get(index).markDone();
        System.out.println("Penguin: The following task has been marked.\n" +
                "[" + tasks.get(index).getStatus() + "] " +
                tasks.get(index));
    }

    /** Marks the task at the specified index as incomplete and displays it.
     *
     * @param index zero-based index of the task to unmark
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void unmarkTask(int index) {
        tasks.get(index).markUndone();
        System.out.println("Penguin: The following task has been unmarked.\n" +
                "[" + tasks.get(index).getStatus() + "] " +
                tasks.get(index));
    }

    /** Displays all tasks in their insertion order. */
    public void listTasks() {
        for (int i = 0; i < size(); i++) {
            System.out.println((i + 1) + ". " +
                    "[" + tasks.get(i).getStatus() + "] " +
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

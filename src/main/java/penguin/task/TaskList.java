package penguin.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

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
        tasks.add(Objects.requireNonNull(task, "Task must not be null"));
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

    /**
     * Returns a copy of the current tasks for display purposes.
     *
     * @return copy of the current task list
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
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
    public ArrayList<String> toStorageLines() {
        return tasks.stream().map(Task::toStorageFormat)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Finds deadlines and events occurring on a specified date.
     *
     * @param date date to search for
     * @return tasks occurring on the specified date
     */
    public ArrayList<Task> findTasksOnDate(LocalDate date) {
        return tasks.stream().filter(task -> task.occursOn(date))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns tasks whose descriptions contain the specified keyword.
     *
     * @param keyword keyword or phrase to search for
     * @return matching tasks in their original order
     */
    public ArrayList<Task> findMatchingTasks(String keyword) {
        return tasks.stream().filter(task -> task.containsKeyword(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

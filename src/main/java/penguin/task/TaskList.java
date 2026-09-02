package penguin.task;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Stores the tasks entered by the user.
 */
public class TaskList {
    private final ArrayList<Task> tasks;
    private static final int MAX_UNDO_STEPS = 5;
    private final Deque<ArrayList<Task>> history = new ArrayDeque<>();

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
        Task validTask = Objects.requireNonNull(task, "Task must not be null");
        saveState();
        tasks.add(validTask);
    }

    /** Adds a task loaded from storage without creating an undo history entry.
     *
     * @param task task restored from persistent storage
     */
    public void addLoadedTask(Task task) {
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
        Task task = tasks.get(index);
        saveState();
        tasks.remove(index);
        return task;
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
        saveState();
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
        saveState();
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
     * Restores the most recent task-list state.
     *
     * @throws IllegalStateException if there is no previous task-list state
     */
    public void undo() {
        if (history.isEmpty()) {
            throw new IllegalStateException("There is nothing to undo.");
        }
        tasks.clear();
        tasks.addAll(history.pop());
    }

    /** Saves an independent state snapshot and keeps only MAX_UNDO_STEPS snapshots. */
    private void saveState() {
        history.push(copyOfTasks());
        if (history.size() > MAX_UNDO_STEPS) {
            history.removeLast();
        }
    }

    /** Creates independent copies of all tasks in their current order. */
    private ArrayList<Task> copyOfTasks() {
        return tasks.stream().map(Task::copy)
                .collect(Collectors.toCollection(ArrayList::new));
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

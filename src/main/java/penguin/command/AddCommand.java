package penguin.command;

import penguin.exception.PenguinException;
import penguin.storage.Storage;
import penguin.task.Task;
import penguin.task.TaskList;
import penguin.ui.Ui;

/** Represents a command that adds a task. */
public class AddCommand extends Command {
    /** Task to add. */
    private final Task task;

    /**
     * Creates an add command.
     *
     * @param newTask task to add
     */
    public AddCommand(Task newTask) {
        this.task = newTask;
    }

    /** Adds the task, persists the updated list, and reports the result. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        tasks.addTask(task);
        storage.saveTaskLines(tasks.toStorageLines());
        ui.showMessage("I have added '" + task + "' to your list of tasks."
                + " Now you have " + tasks.size() + " task(s) in the list.");
    }
}

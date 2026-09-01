package penguin.command;

import penguin.exception.PenguinException;
import penguin.storage.Storage;
import penguin.task.Task;
import penguin.task.TaskList;
import penguin.ui.Ui;

/** Represents a command that deletes a task. */
public class DeleteCommand extends Command {
    /** Zero-based task index to delete. */
    private final int index;

    /**
     * Creates a delete command.
     *
     * @param taskIndex zero-based task index
     */
    public DeleteCommand(int taskIndex) {
        this.index = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        try {
            Task task = tasks.deleteTask(index);
            storage.saveTaskLines(tasks.toStorageLines());
            ui.showMessage("I have removed '" + task
                    + "' from your list of tasks. Now you have "
                    + tasks.size() + " task(s) in the list.");
        } catch (IndexOutOfBoundsException e) {
            throw new PenguinException("Invalid task index!");
        }
    }
}

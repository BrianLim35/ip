package penguin.command;

import penguin.exception.PenguinException;
import penguin.storage.Storage;
import penguin.task.Task;
import penguin.task.TaskList;
import penguin.ui.Ui;

/** Represents a command that marks a task as incomplete. */
public class UnmarkCommand extends Command {
    /** Zero-based task index to unmark. */
    private final int index;

    /**
     * Creates an unmark command.
     *
     * @param taskIndex zero-based task index
     */
    public UnmarkCommand(int taskIndex) {
        this.index = taskIndex;
    }

    /** Marks the selected task as incomplete and persists the updated list. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        try {
            Task task = tasks.unmarkTask(index);
            storage.saveTaskLines(tasks.toStorageLines());
            ui.showMessage("The following task has been unmarked.\n" + task);
        } catch (IndexOutOfBoundsException e) {
            throw new PenguinException("Invalid task index!");
        }
    }
}

package penguin.command;

import penguin.exception.PenguinException;
import penguin.storage.Storage;
import penguin.task.TaskList;
import penguin.ui.Ui;

/** Represents an executable Penguin command. */
public abstract class Command {
    /** Creates a command. */
    protected Command() {
    }

    /**
     * Executes the command.
     *
     * @param tasks task list to modify or query
     * @param ui interface used for output
     * @param storage storage used to persist changes
     * @throws PenguinException if execution fails
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
            throws PenguinException;

    /**
     * Checks whether this command exits the application.
     *
     * @return true if this command exits Penguin
     */
    public boolean isExit() {
        return false;
    }
}

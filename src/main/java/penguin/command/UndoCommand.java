package penguin.command;

import penguin.exception.PenguinException;
import penguin.storage.Storage;
import penguin.task.TaskList;
import penguin.ui.Ui;

/** Represents a command that reverses the most recent task-list change. */
public class UndoCommand extends Command {
    /** Restores the previous task-list state and persists it. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        assert tasks != null : "Undo command requires a task list";
        assert ui != null : "Undo command requires a user interface";
        assert storage != null : "Undo command requires storage";
        try {
            tasks.undo();
            storage.saveTaskLines(tasks.toStorageLines());
            ui.showMessage("The previous action has been undone.");
        } catch (IllegalStateException e) {
            throw new PenguinException(e.getMessage());
        }
    }
}

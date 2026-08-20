package penguin.command;

import penguin.storage.Storage;
import penguin.task.TaskList;
import penguin.ui.Ui;

/** Represents a command that exits Penguin. */
public class ExitCommand extends Command {
    /** Creates an exit command. */
    public ExitCommand() {
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

package penguin.command;

import penguin.storage.Storage;
import penguin.task.TaskList;
import penguin.ui.Ui;

/** Represents a command that lists all tasks. */
public class ListCommand extends Command {
    /** Creates a list command. */
    public ListCommand() {
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks);
    }
}

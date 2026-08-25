package penguin.command;

import penguin.storage.Storage;
import penguin.task.TaskList;
import penguin.ui.Ui;

/** Represents a command that finds tasks containing a keyword. */
public class FindCommand extends Command {
    /** Keyword used to filter tasks. */
    private final String keyword;

    /**
     * Creates a keyword-search command.
     *
     * @param searchKeyword keyword or phrase to search for
     */
    public FindCommand(String searchKeyword) {
        this.keyword = searchKeyword;
    }

    /**
     * Displays tasks whose descriptions contain the search keyword.
     *
     * @param tasks task list to search
     * @param ui interface used for output
     * @param storage storage used by the command framework
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasksFind(keyword, tasks.getTasksMatch(keyword));
    }
}

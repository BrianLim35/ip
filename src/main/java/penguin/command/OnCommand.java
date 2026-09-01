package penguin.command;

import penguin.storage.Storage;
import penguin.task.TaskList;
import penguin.ui.Ui;

import java.time.LocalDate;

/** Represents a command that finds dated tasks. */
public class OnCommand extends Command {
    /** Date to search for. */
    private final LocalDate date;

    /**
     * Creates a date-search command.
     *
     * @param searchDate date to search for
     */
    public OnCommand(LocalDate searchDate) {
        this.date = searchDate;
    }

    /** Displays tasks occurring on the configured date. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasksOnDate(date, tasks.findTasksOnDate(date));
    }
}

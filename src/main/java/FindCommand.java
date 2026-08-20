import java.time.LocalDate;

/** Represents a command that finds dated tasks. */
public class FindCommand extends Command {
    /** Date to search for. */
    private final LocalDate date;

    /**
     * Creates a date-search command.
     *
     * @param date date to search for
     */
    public FindCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasksOn(date, tasks.getTasksOn(date));
    }
}

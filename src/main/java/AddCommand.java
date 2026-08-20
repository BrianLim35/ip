/** Represents a command that adds a task. */
public class AddCommand extends Command {
    /** Task to add. */
    private final Task task;

    /**
     * Creates an add command.
     *
     * @param task task to add
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws PenguinException {
        tasks.addTask(task);
        storage.save(tasks.toFileLines());
        ui.showMessage("I have added '" + task + "' to your list of tasks."
                + " Now you have " + tasks.size() + " task(s) in the list.");
    }
}

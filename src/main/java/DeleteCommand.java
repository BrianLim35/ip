/** Represents a command that deletes a task. */
public class DeleteCommand extends Command {
    /** Zero-based task index to delete. */
    private final int index;

    /**
     * Creates a delete command.
     *
     * @param index zero-based task index
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws PenguinException {
        try {
            Task task = tasks.deleteTask(index);
            storage.save(tasks.toFileLines());
            ui.showMessage("I have removed '" + task
                    + "' from your list of tasks. Now you have "
                    + tasks.size() + " task(s) in the list.");
        } catch (IndexOutOfBoundsException e) {
            throw new PenguinException("Invalid task index!");
        }
    }
}

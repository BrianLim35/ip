/** Represents a command that marks a task as completed. */
public class MarkCommand extends Command {
    /** Zero-based task index to mark. */
    private final int index;

    /**
     * Creates a mark command.
     *
     * @param index zero-based task index
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws PenguinException {
        try {
            Task task = tasks.markTask(index);
            storage.save(tasks.toFileLines());
            ui.showMessage("The following task has been marked.\n" + task);
        } catch (IndexOutOfBoundsException e) {
            throw new PenguinException("Invalid task index!");
        }
    }
}

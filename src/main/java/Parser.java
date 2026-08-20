import java.time.LocalDateTime;

/**
 * Converts saved task data into Task objects.
 */
public class Parser {

    /** Prevents instantiation of this utility class. */
    private Parser() {
    }

    /**
     * Converts one saved line into a task.
     *
     * @param line saved task data
     * @return task represented by the line
     * @throws PenguinException if the line is malformed
     */
    public static Task parseTask(String line) throws PenguinException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3 || parts[2].isBlank()) {
            throw new PenguinException("Invalid task data. File may be corrupted.");
        }

        String type = parts[0];
        String status = parts[1].trim();
        if (!status.equals("0") && !status.equals("1")) {
            throw new PenguinException("Invalid task status. File may be corrupted.");
        }
        boolean isDone = status.equals("1");
        String description = parts[2];

        if (description.contains("|")) {
            throw new PenguinException("Invalid task data! " +
                    "Descriptions cannot contain the | character.");
        }

        Task task = switch (type) {
            case "T" -> {
                if (parts.length != 3) {
                    throw new PenguinException("Invalid todo data.");
                }
                yield new ToDo(description);
            }
            case "D" -> {
                if (parts.length != 4 || parts[3].isBlank()) {
                    throw new PenguinException("Invalid deadline data.");
                }
                LocalDateTime dateTime = DateTimeUtil.parse(parts[3]);
                yield new Deadline(description, dateTime);
            }
            case "E" -> {
                if (parts.length != 5 || parts[3].isBlank() || parts[4].isBlank()) {
                    throw new PenguinException("Invalid event data.");
                }
                LocalDateTime from = DateTimeUtil.parse(parts[3]);
                LocalDateTime to = DateTimeUtil.parse(parts[4]);

                if (to.isBefore(from)) {
                    throw new PenguinException("Invalid event data. End is before start.");
                }

                yield new Event(description, from, to);
            }
            default -> throw new PenguinException("Unknown task type.");
        };

        if (isDone) {
            task.markDone();
        }

        return task;
    }
}

package penguin.parser;

import penguin.command.Command;
import penguin.command.AddCommand;
import penguin.command.DeleteCommand;
import penguin.command.ExitCommand;
import penguin.command.ListCommand;
import penguin.command.MarkCommand;
import penguin.command.UnmarkCommand;
import penguin.command.OnCommand;
import penguin.command.FindCommand;
import penguin.exception.PenguinException;
import penguin.task.Deadline;
import penguin.task.Event;
import penguin.task.Task;
import penguin.task.ToDo;
import penguin.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Converts saved task data into Task objects.
 */
public class Parser {

    /** Prevents instantiation of this utility class. */
    private Parser() {
    }

    /**
     * Converts a user command into an executable command.
     *
     * @param input complete user command
     * @return executable command
     * @throws PenguinException if the command is invalid
     */
    public static Command parseCommand(String input) throws PenguinException {
        String trimmedInput = input.trim();
        String firstWord = trimmedInput.split("\\s+", 2)[0].toLowerCase();

        if (firstWord.equals("bye")
                && !trimmedInput.equalsIgnoreCase("bye")) {
            throw new PenguinException("The bye command does not take arguments.");
        }

        return switch (firstWord) {
            case "todo", "deadline", "event" ->
                    new AddCommand(parseTaskCommand(trimmedInput));
            case "list" -> {
                if (!trimmedInput.equalsIgnoreCase("list")) {
                    throw new PenguinException("The list command does not take arguments.");
                }
                yield new ListCommand();
            }
            case "mark" -> new MarkCommand(parseTaskIndex(trimmedInput));
            case "unmark" -> new UnmarkCommand(parseTaskIndex(trimmedInput));
            case "delete" -> new DeleteCommand(parseTaskIndex(trimmedInput));
            case "on" -> new OnCommand(parseDate(trimmedInput));
            case "find" -> new FindCommand(parseKeyword(trimmedInput));
            case "bye" -> new ExitCommand();
            default -> throw new PenguinException("I don't understand that command.");
        };
    }

    /**
     * Parses the task index from a command.
     *
     * @param command command containing a task number
     * @return zero-based task index
     * @throws PenguinException if the task number is invalid
     */
    private static int parseTaskIndex(String command) throws PenguinException {
        String[] parts = command.split("\\s+");

        if (parts.length != 2) {
            throw new PenguinException("Please enter a task number.");
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new PenguinException("Please enter a valid task number.");
        }
    }

    /**
     * Parses the keyword or phrase from a find command.
     *
     * @param command command containing a keyword or phrase
     * @return trimmed keyword or phrase
     * @throws PenguinException if no keyword is provided
     */
    private static String parseKeyword(String command) throws PenguinException {
        String[] parts = command.split("\\s+", 2);

        if (parts.length != 2 || parts[1].isBlank()) {
            throw new PenguinException("Please enter a keyword.");
        }

        return parts[1].trim().replaceAll("\\s+", " ");
    }

    /**
     * Parses the date from an on command.
     *
     * @param command command containing one date
     * @return parsed date
     * @throws PenguinException if the date is invalid
     */
    private static LocalDate parseDate(String command) throws PenguinException {
        String[] parts = command.split("\\s+");

        if (parts.length != 2) {
            throw new PenguinException(
                    "The on command requires one date in yyyy-MM-dd format.");
        }

        return DateTimeUtil.parseDate(parts[1]);
    }

    /**
     * Converts a task-creation command into a task.
     *
     * @param command complete task-creation command
     * @return created task
     * @throws PenguinException if the command is invalid
     */
    private static Task parseTaskCommand(String command) throws PenguinException {
        String lowerCaseCommand = command.toLowerCase();

        if (lowerCaseCommand.equals("todo")
                || lowerCaseCommand.startsWith("todo ")) {
            return parseTodoCommand(command);
        }

        if (lowerCaseCommand.equals("deadline")
                || lowerCaseCommand.startsWith("deadline ")) {
            return parseDeadlineCommand(command);
        }

        if (lowerCaseCommand.equals("event")
                || lowerCaseCommand.startsWith("event ")) {
            return parseEventCommand(command);
        }

        throw new PenguinException("I don't understand that command.");
    }

    /** Parses a to-do creation command. */
    private static Task parseTodoCommand(String command) throws PenguinException {
        String description = command.length() <= 4 ? "" : command.substring(4).trim();
        if (description.isEmpty()) {
            throw new PenguinException("The description of a todo cannot be empty.");
        }
        validateDescription(description);
        return new ToDo(description);
    }

    /** Parses a deadline creation command. */
    private static Task parseDeadlineCommand(String command) throws PenguinException {
        String content = command.length() <= 9 ? "" : command.substring(9);
        String separator = " /by";
        String lowerCaseContent = content.toLowerCase();

        if (content.trim().isEmpty() || lowerCaseContent.trim().equals("/by")
                || lowerCaseContent.trim().startsWith("/by ")) {
            throw new PenguinException("The description of a deadline cannot be empty.");
        }
        if (!lowerCaseContent.contains(separator)) {
            throw new PenguinException("A deadline must contain /by followed by a date or time.");
        }
        if (lowerCaseContent.indexOf(separator) != lowerCaseContent.lastIndexOf(separator)) {
            throw new PenguinException("A deadline must contain only one /by separator.");
        }

        int separatorIndex = lowerCaseContent.indexOf(separator);
        String description = content.substring(0, separatorIndex).trim();
        String dateTimeInput = content.substring(separatorIndex + separator.length()).trim();
        if (description.isEmpty()) {
            throw new PenguinException("A deadline must have a description before /by.");
        }
        validateDescription(description);
        if (dateTimeInput.isEmpty()) {
            throw new PenguinException("A deadline must have a date or time after /by.");
        }

        LocalDateTime dateTime = DateTimeUtil.parseDateTime(dateTimeInput);
        DateTimeUtil.validateNotBeforeToday(dateTime, "deadline");
        return new Deadline(description, dateTime);
    }

    /** Parses an event creation command. */
    private static Task parseEventCommand(String command) throws PenguinException {
        String content = command.length() <= 6 ? "" : command.substring(6);
        String fromSeparator = " /from";
        String toSeparator = " /to";
        String lowerCaseContent = content.toLowerCase();

        if (lowerCaseContent.startsWith("/from ") || lowerCaseContent.startsWith("/to ")) {
            throw new PenguinException("The description of an event cannot be empty.");
        }

        int fromIndex = lowerCaseContent.indexOf(fromSeparator);
        int toIndex = lowerCaseContent.indexOf(toSeparator);
        if (fromIndex < 0 || toIndex < 0 || fromIndex > toIndex
                || fromIndex != lowerCaseContent.lastIndexOf(fromSeparator)
                || toIndex != lowerCaseContent.lastIndexOf(toSeparator)) {
            throw new PenguinException("An event must contain one /from and one /to separator.");
        }

        String description = content.substring(0, fromIndex).trim();
        String fromInput = content.substring(fromIndex + fromSeparator.length(), toIndex).trim();
        String toInput = content.substring(toIndex + toSeparator.length()).trim();
        if (description.isEmpty()) {
            throw new PenguinException("The description of an event cannot be empty.");
        }
        validateDescription(description);
        if (fromInput.isEmpty() || toInput.isEmpty()) {
            throw new PenguinException("An event must have both a start and an end time.");
        }

        LocalDateTime from = DateTimeUtil.parseDateTime(fromInput);
        LocalDateTime to = DateTimeUtil.parseDateTime(toInput);
        DateTimeUtil.validateNotBeforeToday(to, "event end");
        if (to.isBefore(from)) {
            throw new PenguinException("The start time must be before the end time.");
        }
        return new Event(description, from, to);
    }

    /**
     * Rejects the persistence delimiter in a user description.
     *
     * @param description task description
     * @throws PenguinException if the delimiter is present
     */
    private static void validateDescription(String description) throws PenguinException {
        if (description.contains("|")) {
            throw new PenguinException(
                    "Task descriptions cannot contain the | character.");
        }
    }

    /**
     * Converts one saved line into a task.
     *
     * @param line saved task data
     * @return task represented by the line
     * @throws PenguinException if the line is malformed
     */
    public static Task parseSavedTask(String line) throws PenguinException {
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

        Task task = createSavedTask(type, parts, description);

        if (isDone) {
            task.markDone();
        }

        return task;
    }

    /** Creates a task from its validated saved-data fields. */
    private static Task createSavedTask(String type, String[] parts, String description)
            throws PenguinException {
        return switch (type) {
            case "T" -> parseSavedTodo(parts, description);
            case "D" -> parseSavedDeadline(parts, description);
            case "E" -> parseSavedEvent(parts, description);
            default -> throw new PenguinException("Unknown task type.");
        };
    }

    /** Parses a saved to-do record. */
    private static Task parseSavedTodo(String[] parts, String description)
            throws PenguinException {
        if (parts.length != 3) {
            throw new PenguinException("Invalid todo data.");
        }
        return new ToDo(description);
    }

    /** Parses a saved deadline record. */
    private static Task parseSavedDeadline(String[] parts, String description)
            throws PenguinException {
        if (parts.length != 4 || parts[3].isBlank()) {
            throw new PenguinException("Invalid deadline data.");
        }
        LocalDateTime dateTime = DateTimeUtil.parseDateTime(parts[3]);
        DateTimeUtil.validateNotBeforeToday(dateTime, "deadline");
        return new Deadline(description, dateTime);
    }

    /** Parses a saved event record. */
    private static Task parseSavedEvent(String[] parts, String description)
            throws PenguinException {
        if (parts.length != 5 || parts[3].isBlank() || parts[4].isBlank()) {
            throw new PenguinException("Invalid event data.");
        }
        LocalDateTime from = DateTimeUtil.parseDateTime(parts[3]);
        LocalDateTime to = DateTimeUtil.parseDateTime(parts[4]);
        DateTimeUtil.validateNotBeforeToday(to, "event end");
        if (to.isBefore(from)) {
            throw new PenguinException("Invalid event data. End is before start.");
        }
        return new Event(description, from, to);
    }
}

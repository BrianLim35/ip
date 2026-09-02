package penguin.parser;

import java.time.LocalDate;
import java.util.Locale;

import penguin.command.AddCommand;
import penguin.command.Command;
import penguin.command.DeleteCommand;
import penguin.command.ExitCommand;
import penguin.command.FindCommand;
import penguin.command.ListCommand;
import penguin.command.MarkCommand;
import penguin.command.OnCommand;
import penguin.command.UnmarkCommand;
import penguin.command.UndoCommand;
import penguin.enums.CommandType;
import penguin.exception.PenguinException;
import penguin.task.Task;
import penguin.util.DateTimeUtil;

/** Parses user commands and saved task records into application objects. */
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
        if (input == null) {
            throw new PenguinException("Please input a task.");
        }
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw new PenguinException("Please input a task.");
        }
        String firstWord = trimmedInput.split("\\s+", 2)[0].toLowerCase(Locale.ROOT);

        CommandType commandType = CommandType.fromKeyword(firstWord);
        if (commandType == CommandType.BYE
                && !trimmedInput.equalsIgnoreCase(commandType.name())) {
            throw new PenguinException("The bye command does not take arguments.");
        }

        if (commandType == null) {
            throw new PenguinException("I don't understand that command.");
        }

        return switch (commandType) {
            case TODO, DEADLINE, EVENT ->
                    new AddCommand(parseTaskCommand(trimmedInput));
            case LIST -> {
                if (!trimmedInput.equalsIgnoreCase(commandType.name())) {
                    throw new PenguinException("The list command does not take arguments.");
                }
                yield new ListCommand();
            }
            case MARK -> new MarkCommand(parseTaskIndex(trimmedInput));
            case UNMARK -> new UnmarkCommand(parseTaskIndex(trimmedInput));
            case DELETE -> new DeleteCommand(parseTaskIndex(trimmedInput));
            case ON -> new OnCommand(parseDate(trimmedInput));
            case FIND -> new FindCommand(parseKeyword(trimmedInput));
            case UNDO -> {
                if (!trimmedInput.equalsIgnoreCase(commandType.name())) {
                    throw new PenguinException("The undo command does not take arguments.");
                }
                yield new UndoCommand();
            }
            case BYE -> new ExitCommand();
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
        return TaskCommandParser.parse(command);
    }

    /**
     * Converts one saved line into a task.
     *
     * @param line saved task data
     * @return task represented by the line
     * @throws PenguinException if the line is malformed
     */
    public static Task parseSavedTask(String line) throws PenguinException {
        return SavedTaskParser.parse(line);
    }
}

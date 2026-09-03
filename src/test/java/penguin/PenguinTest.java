package penguin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests complete Penguin workflows through its console interface. */
class PenguinTest {
    @Test
    void run_byeCommand_showsGreetingAndGoodbye(@TempDir Path tempDir) {
        String output = runDirectorySession(tempDir, "bye\n");

        assertTrue(output.contains("Hello! I'm Penguin."));
        assertTrue(output.contains("Bye. Hope to see you again soon!"));
    }

    @Test
    void run_invalidCommands_preservesEmptyTaskList(@TempDir Path tempDir) {
        String output = runDirectorySession(tempDir,
                "\nlist now\nmark\ndelete abc\nbye now\nlist\nbye\n");

        assertTrue(output.contains("Please input a task."));
        assertTrue(output.contains("Your task list is empty!"));
        assertFalse(output.contains("I have added"));
    }

    @Test
    void run_caseInsensitiveTypedCommands_displaysAllTaskTypes(
            @TempDir Path tempDir) {
        String output = runDirectorySession(tempDir,
                "TODO buy groceries\n"
                        + "DEADLINE pay bills /BY 2099-12-26 1800\n"
                        + "EVENT dentist /FROM 2099-12-26 1400 "
                        + "/TO 2099-12-26 1600\nlist\nbye\n");

        assertTrue(output.contains("[T][ ] buy groceries"));
        assertTrue(output.contains("[D][ ] pay bills"));
        assertTrue(output.contains("[E][ ] dentist"));
    }

    @Test
    void run_reservedDelimiter_rejectsBothDescriptionForms(
            @TempDir Path tempDir) {
        String output = runDirectorySession(tempDir,
                "todo read | book\ntodo read|book\nlist\nbye\n");

        assertTrue(output.contains("Task descriptions cannot contain"));
        assertTrue(output.contains("Your task list is empty!"));
    }

    @Test
    void run_dateSearch_displaysMatchingDatedTasksOnly(@TempDir Path tempDir) {
        String output = runDirectorySession(tempDir,
                "todo read book\n"
                        + "deadline submit report /by 2099-12-26 1800\n"
                        + "event project meeting /from 2099-12-26 1400 "
                        + "/to 2099-12-26 1600\n"
                        + "on 2099-12-26\nbye\n");

        assertTrue(output.contains("submit report"));
        assertTrue(output.contains("project meeting"));
        assertFalse(output.contains("1. [T][ ] read book"));
    }

    @Test
    void run_findCommand_displaysMatchingTasksOnly(@TempDir Path tempDir) {
        String output = runDirectorySession(tempDir,
                "todo read book\n"
                        + "todo return laptop\n"
                        + "deadline submit report /by 2099-12-26 1800\n"
                        + "find read book\nlist\nbye\n");

        assertTrue(output.contains("1. [T][ ] read book"));
        assertFalse(output.contains("1. [T][ ] return laptop"));
        assertFalse(output.contains("1. [D][ ] submit report"));
    }

    @Test
    void run_findCommand_noMatches_showsNoMatchMessage(
            @TempDir Path tempDir) {
        String output = runDirectorySession(tempDir,
                "todo read book\nfind laptop\nbye\n");

        assertTrue(output.contains(
                "No tasks found when searching for laptop."));
    }

    @Test
    void run_findCommand_caseSensitiveKeyword_matchesExactCase(
            @TempDir Path tempDir) {
        String output = runDirectorySession(tempDir,
                "todo Read book\nfind read\nbye\n");

        assertTrue(output.contains("No tasks found when searching for read."));
    }

    @Test
    void run_restart_restoresTasksAndState(@TempDir Path tempDir) {
        runDirectorySession(tempDir, "todo read book\nmark 1\nbye\n");

        String output = runDirectorySession(tempDir, "list\nbye\n");

        assertTrue(output.contains("[T][X] read book"));
    }

    @Test
    void load_corruptedRecord_skipsItAndLoadsValidRecords(
            @TempDir Path tempDir) throws Exception {
        Path storagePath = tempDir.resolve("data/penguin.txt");
        Files.createDirectories(storagePath.getParent());
        Files.writeString(storagePath,
                "T | 2 | invalid\nT | 0 | valid task\n");

        String output = runSession(storagePath, "list\nbye\n");

        assertTrue(output.contains("Skipping invalid saved task!"));
        assertTrue(output.contains("valid task"));
        assertFalse(output.contains("[T][ ] invalid"));
    }

    @Test
    void run_eventBeforeTodayWithFutureEnd_acceptsEvent(@TempDir Path tempDir) {
        String output = runDirectorySession(tempDir,
                "event ongoing project /from 2026-08-19 1400 "
                        + "/to 2099-12-26 1600\nlist\nbye\n");

        assertTrue(output.contains("ongoing project"));
    }

    @Test
    void getResponse_blankInput_returnsInputError(@TempDir Path tempDir) {
        Penguin penguin = new Penguin(
                tempDir.resolve("data/penguin.txt").toString());

        assertTrue(penguin.getResponse("   ").contains("Please input a task."));
        assertFalse(penguin.isExitRequested());
    }

    @Test
    void getResponse_byeCommand_returnsGoodbyeAndRequestsExit(
            @TempDir Path tempDir) {
        Penguin penguin = new Penguin(
                tempDir.resolve("data/penguin.txt").toString());

        assertTrue(penguin.getResponse("bye").contains(
                "Bye. Hope to see you again soon!"));
        assertTrue(penguin.isExitRequested());
    }

    @Test
    void getResponse_invalidCommand_returnsErrorWithoutExit(
            @TempDir Path tempDir) {
        Penguin penguin = new Penguin(
                tempDir.resolve("data/penguin.txt").toString());

        assertTrue(penguin.getResponse("unknown command").contains(
                "I don't understand that command."));
        assertFalse(penguin.isExitRequested());
    }

    /** Runs one isolated console session and returns its output. */
    private String runDirectorySession(Path directory, String input) {
        return runSession(directory.resolve("data/penguin.txt"), input);
    }

    /** Runs one isolated console session and returns its output. */
    private String runSession(Path storagePath, String input) {
        return runSession(storagePath.toString(), input);
    }

    /** Runs one isolated console session and returns its output. */
    private String runSession(String storagePath, String input) {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(
                    input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output));
            new Penguin(storagePath).run();
            return output.toString();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}

package penguin.ui;

import org.junit.jupiter.api.Test;
import penguin.task.TaskList;
import penguin.task.ToDo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UiTest {
    @Test
    void showMessage_validMessage_printsPenguinPrefix() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));
            new Ui().showMessage("Test message.");
        } finally {
            System.setOut(originalOutput);
        }

        assertTrue(output.toString().contains("Penguin: Test message."));
    }

    @Test
    void showTasks_emptyList_showsEmptyMessage() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));
            new Ui().showTasks(new TaskList());
        } finally {
            System.setOut(originalOutput);
        }

        assertTrue(output.toString().contains("Your task list is empty!"));
    }

    @Test
    void showTasksOn_noMatchingTasks_showsNoTasksMessage() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));
            new Ui().showTasksOn(LocalDate.of(2099, 12, 31),
                    new ArrayList<>());
        } finally {
            System.setOut(originalOutput);
        }

        assertTrue(output.toString().contains(
                "No deadlines or events occur on 31 Dec 2099."));
    }

    @Test
    void showTasks_nonEmptyList_displaysNumberedTasks() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("read book"));

        try {
            System.setOut(new PrintStream(output));
            new Ui().showTasks(tasks);
        } finally {
            System.setOut(originalOutput);
        }

        assertTrue(output.toString().contains("1. [T][ ] read book"));
    }

    @Test
    void showError_validMessage_usesPenguinPrefix() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));
            new Ui().showError("Invalid command.");
        } finally {
            System.setOut(originalOutput);
        }

        assertTrue(output.toString().contains("Penguin: Invalid command."));
    }
}

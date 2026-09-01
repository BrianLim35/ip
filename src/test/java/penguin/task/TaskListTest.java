package penguin.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskListTest {
    @Test
    void addAndDelete_validTask_updatesTaskList() {
        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("read book"));

        assertEquals(1, tasks.size());
        assertEquals("[T][ ] read book", tasks.deleteTask(0).toString());
        assertEquals(0, tasks.size());
    }

    @Test
    void markTask_validIndex_updatesCompletionStatus() {
        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("read book"));

        tasks.markTask(0);

        assertEquals("[T][X] read book", tasks.getTasks().get(0).toString());
    }

    @Test
    void getTasksOn_matchingDate_returnsOnlyDatedTasks() {
        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("read book"));
        tasks.addTask(new Deadline("submit report",
                LocalDateTime.of(2099, 12, 31, 18, 0)));

        assertEquals(1, tasks.findTasksOnDate(LocalDate.of(2099, 12, 31)).size());
    }

    @Test
    void deleteTask_invalidIndex_throwsIndexException() {
        TaskList tasks = new TaskList();

        assertThrows(IndexOutOfBoundsException.class,
                () -> tasks.deleteTask(0));
    }

    @Test
    void emptyTaskList_noTasks_reportsEmptyAndNoStorageLines() {
        TaskList tasks = new TaskList();

        assertEquals(0, tasks.size());
        assertEquals(0, tasks.getTasks().size());
        assertEquals(0, tasks.toStorageLines().size());
    }

    @Test
    void unmarkTask_completedTask_restoresIncompleteStatus() {
        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("read book"));
        tasks.markTask(0);

        tasks.unmarkTask(0);

        assertEquals("[T][ ] read book", tasks.getTasks().get(0).toString());
    }

    @Test
    void getTasksOn_spanningEvent_returnsEventOnDate() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Event("conference",
                LocalDateTime.of(2099, 12, 30, 9, 0),
                LocalDateTime.of(2099, 12, 31, 17, 0)));

        assertEquals(1, tasks.findTasksOnDate(LocalDate.of(2099, 12, 31)).size());
    }

    @Test
    void getTasksOn_eventStartingOnDate_includesEvent() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Event("conference",
                LocalDateTime.of(2099, 12, 31, 9, 0),
                LocalDateTime.of(2099, 12, 31, 17, 0)));

        assertEquals(1, tasks.findTasksOnDate(LocalDate.of(2099, 12, 31)).size());
    }

    @Test
    void getTasksOn_eventOutsideDate_excludesEvent() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Event("conference",
                LocalDateTime.of(2099, 12, 30, 9, 0),
                LocalDateTime.of(2099, 12, 30, 17, 0)));

        assertEquals(0, tasks.findTasksOnDate(LocalDate.of(2099, 12, 31)).size());
    }

    @Test
    void deleteTask_validIndex_preservesRemainingOrder() {
        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("first task"));
        tasks.addTask(new ToDo("second task"));
        tasks.addTask(new ToDo("third task"));

        tasks.deleteTask(1);

        assertEquals("[T][ ] first task", tasks.getTasks().get(0).toString());
        assertEquals("[T][ ] third task", tasks.getTasks().get(1).toString());
    }
}

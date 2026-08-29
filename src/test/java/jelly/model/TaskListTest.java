package jelly.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Tests task storage and list operations performed by {@link TaskList}. */
class TaskListTest {

    @Test
    void addTask_taskIsAddedAndSizeIncreases() {
        TaskList taskList = new TaskList();
        Task task = new Todo("buy groceries");

        taskList.addTask(task);

        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    void deleteTask_taskIsRemovedAndReturned() {
        Task firstTask = new Todo("buy groceries");
        Task secondTask = new Todo("read book");
        TaskList taskList = new TaskList(List.of(firstTask, secondTask));

        Task deletedTask = taskList.deleteTask(0);

        assertEquals(firstTask, deletedTask);
        assertEquals(1, taskList.size());
        assertEquals(secondTask, taskList.getTask(0));
    }

    @Test
    void asList_returnedListCannotBeModified() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("buy groceries"));

        List<Task> tasks = taskList.asList();

        assertThrows(
                UnsupportedOperationException.class,
                () -> tasks.add(new Todo("read book"))
        );
        assertEquals(1, taskList.size());
    }

    @Test
    void getTask_invalidIndex_throwsException() {
        TaskList taskList = new TaskList();

        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(0));
    }

    @Test
    void deleteTask_invalidIndex_throwsException() {
        TaskList taskList = new TaskList();

        assertThrows(IndexOutOfBoundsException.class, () -> taskList.deleteTask(0));
    }
}

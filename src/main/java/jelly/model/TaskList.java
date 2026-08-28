package jelly.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Owns Jelly's collection of tasks and its basic list operations. */
public class TaskList {
    /** The tasks managed by this list. */
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Creates a task list containing the supplied tasks. */
    public TaskList(Collection<? extends Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Adds a task to the list. */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /** Removes and returns the task at a zero-based index. */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /** Returns the task at a zero-based index. */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /** Returns the number of tasks. */
    public int size() {
        return tasks.size();
    }

    /** Provides read-only access for rendering and persistence. */
    public List<Task> asList() {
        return List.copyOf(tasks);
    }
}

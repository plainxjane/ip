package jelly.model;

/** Represents a task stored in Jelly's task list. */
public class Task {
    /** The text describing the task. */
    private String description;
    /** Whether the task has been completed. */
    private boolean isDone;

    /** Creates an incomplete task.
     *
     * @param description the text describing the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as completed. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as incomplete. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /** Returns {@code X} if completed, or a blank space otherwise. */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /** Returns the task description. */
    public String getDescription() {
        return this.description;
    }

    /** Returns whether this task has been completed. */
    public boolean isDone() {
        return this.isDone;
    }

    /** Returns the display form of this task. */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }
}

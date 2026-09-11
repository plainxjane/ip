package jelly.model;

/**
 * Represents a task stored in Jelly's task list.
 */
public class Task {
    /**
     * The text describing the task.
     */
    private final String description;
    /**
     * Whether the task has been completed.
     */
    private boolean isDone;
    /**
     * The optional tag attached to this task.
     */
    private String tag;

    /**
     * Creates an incomplete task.
     *
     * @param description the text describing the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.tag = null;
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

    /**
     * Adds a tag to this task.
     *
     * @param tag the tag to assign, without the {@code #} symbol
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /** Removes tag from this task. */
    public void removeTag() {
        this.tag = null;
    }

    /** Returns the tag associated with this task. */
    public String getTag() {
        return this.tag;
    }

    /**
     * Checks if this task has a tag.
     *
     * @return true if this task has a tag.
     */
    public boolean hasTag() {
        return this.tag != null;
    }

    /** Returns the display form of this task. */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " +
                this.getDescription() + "{# " + this.tag + "}";
    }
}

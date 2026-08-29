package jelly.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a task that must be completed by a specified date or time. */
public class Deadline extends Task {
    /** The date or time by which the task should be completed. */
    protected LocalDateTime by;
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /** Creates an incomplete deadline task.
     *
     * @param description the text describing the deadline
     * @param by the deadline date or time
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /** Returns the deadline date or time. */
    public LocalDateTime getBy() {
        return this.by;
    }

    /** Returns the display form of this deadline task. */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(DISPLAY_FORMAT) + ")";
    }
}

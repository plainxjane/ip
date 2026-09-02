package jelly.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that takes place during a specified time range.
 */
public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /** The event's starting time. */
    protected LocalDateTime from;
    /** The event's ending time. */
    protected LocalDateTime to;

    /**
     * Creates an incomplete event task.
     *
     * @param description the text describing the event
     * @param from the event's starting time
     * @param to the event's ending time
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /** Returns the event's starting time. */
    public LocalDateTime getFrom() {
        return this.from;
    }

    /** Returns the event's ending time. */
    public LocalDateTime getTo() {
        return this.to;
    }

    /** Returns the display form of this event task. */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(DISPLAY_FORMAT) + " to: "
                + this.to.format(DISPLAY_FORMAT) + ")";
    }
}

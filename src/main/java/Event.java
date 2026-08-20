/** Represents a task that takes place during a specified time range. */
public class Event extends Task {
    /** The event's starting time. */
    protected String from;
    /** The event's ending time. */
    protected String to;

    /** Creates an incomplete event task.
     * @param description the text describing the event
     * @param from the event's starting time
     * @param to the event's ending time
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /** @return the display form of this event task */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}

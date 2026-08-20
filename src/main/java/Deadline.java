/** Represents a task that must be completed by a specified date or time. */
public class Deadline extends Task {
    /** The date or time by which the task should be completed. */
    protected String by;

    /** Creates an incomplete deadline task.
     * @param description the text describing the deadline
     * @param by the deadline date or time
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /** @return the display form of this deadline task */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}

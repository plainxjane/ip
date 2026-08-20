/** Represents a basic to-do task. */
public class Todo extends Task {

    /** Creates an incomplete to-do task.
     * @param description the text describing the to-do
     */
    public Todo(String description) {
        super(description);
    }

    /** @return the display form of this to-do task */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

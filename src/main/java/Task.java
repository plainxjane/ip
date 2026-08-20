public class Task {
    protected String description;
    protected boolean isDone;
    protected String type;
    protected String by;
    protected String from;
    protected String to;

    // normal to-do
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.type = "T";
    }

    // deadline
    public Task(String description, String by) {
        this.description = description;
        this.isDone = false;
        this.type = "D";
        this.by = by;
    }

    // event
    public Task(String description, String from, String to) {
        this.description = description;
        this.isDone = false;
        this.type = "E";
        this.from = from;
        this.to = to;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        String res = "[" + this.type + "] [" + this.getStatusIcon() + "] " + this.getDescription();

        if (this.type.equals("D")) {
            res += " (by: " + this.by + ")";
        } else if (this.type.equals("E")) {
            res += " (from: " + this.from + " to: " + this.to + ")";
        }
        return res;
    }
}

package jelly.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import jelly.model.Deadline;
import jelly.model.Event;
import jelly.model.Task;
import jelly.model.TaskList;
import jelly.model.Todo;
import jelly.util.DateTimeParser;

/**
 * Saves and loads Jelly tasks from local data file.
 */
public class Storage {
    private static final String TODO_TYPE = "T";
    private static final String DEADLINE_TYPE = "D";
    private static final String EVENT_TYPE = "E";
    private static final String DONE_STATUS = "1";
    private static final String NOT_DONE_STATUS = "0";

    /**
     * Location of the tasks' data file, relative to project root.
     */
    private final Path filePath;

    /** Creates storage using a default data file. */
    public Storage() {
        this.filePath = Path.of("data", "jelly.txt");
    }

    /**
     * Loads tasks from the data file.
     *
     * @return the saved tasks, or an empty list if the file does not exist.
     * @throws IOException if the file cannot be read.
     */
    public TaskList load() throws IOException {
        TaskList tasks = new TaskList();

        // If the data file does not exist, handle it as an empty list.
        if (!Files.exists(filePath)) {
            return tasks;
        }

        List<String> lines = Files.readAllLines(filePath);

        for (String line : lines) {
            String[] parts = line.split(" \\| ");

            if (parts.length < 3) {
                continue;
            }

            String type = parts[0];
            boolean isDone = parts[1].equals(DONE_STATUS);
            String description = parts[2];

            Task task;

            if (type.equals(TODO_TYPE)) {
                task = new Todo(description);
            } else if (type.equals(DEADLINE_TYPE) && parts.length >= 4) {
                task = new Deadline(description, DateTimeParser.parse(parts[3]));
            } else if (type.equals(EVENT_TYPE) && parts.length >= 5) {
                task = new Event(description, DateTimeParser.parse(parts[3]), DateTimeParser.parse(parts[4]));
            } else {
                continue;
            }

            if (isDone) {
                task.markAsDone();
            }

            tasks.addTask(task);
        }

        return tasks;
    }

    /**
     * Saves all tasks to the data file.
     *
     * @param tasks tasks to save.
     * @throws IOException if the directory or file cannot be written.
     */
    public void save(TaskList tasks) throws IOException {
        Files.createDirectories(filePath.getParent());

        ArrayList<String> lines = new ArrayList<>();

        assert tasks != null : "Tasklist should not be null";

        for (Task task : tasks.asList()) {
            String line;

            if (task instanceof Todo) {
                line = TODO_TYPE + " | " + statusFor(task) + task.getDescription();
            } else if (task instanceof Deadline deadline) {
                line = DEADLINE_TYPE + " | " + statusFor(task) + task.getDescription()
                        + " | " + deadline.getBy();
            } else if (task instanceof Event event) {
                line = EVENT_TYPE + " | " + statusFor(task) + task.getDescription()
                        + " | " + event.getFrom() + " | " + event.getTo();
            } else {
                continue;
            }

            lines.add(line);
        }

        Files.write(filePath, lines);
    }

    /** Returns the serialized completion status and its field separator. */
    private static String statusFor(Task task) {
        return (task.isDone() ? DONE_STATUS : NOT_DONE_STATUS) + " | ";
    }
}

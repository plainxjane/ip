package jelly.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

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

        for (String line : Files.readAllLines(filePath)) {
            Task task = parseLine(line);
            if (task != null) {
                tasks.addTask(task);
            }
        }

        return tasks;
    }

    /** Parses one saved line into a task, or returns {@code null} if the line is malformed. */
    private static Task parseLine(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        Task task = createTask(parts[0], parts[2], parts);
        if (task == null) {
            return null;
        }

        boolean isDone = parts[1].equals(DONE_STATUS);
        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    /** Creates the task matching the type code, or returns {@code null} if it is unrecognized. */
    private static Task createTask(String type, String description, String[] parts) {
        if (type.equals(TODO_TYPE)) {
            return new Todo(description);
        } else if (type.equals(DEADLINE_TYPE) && parts.length >= 4) {
            return new Deadline(description, DateTimeParser.parse(parts[3]));
        } else if (type.equals(EVENT_TYPE) && parts.length >= 5) {
            return new Event(description, DateTimeParser.parse(parts[3]), DateTimeParser.parse(parts[4]));
        }
        return null;
    }

    /**
     * Saves all tasks to the data file.
     *
     * @param tasks tasks to save.
     * @throws IOException if the directory or file cannot be written.
     */
    public void save(TaskList tasks) throws IOException {
        Files.createDirectories(filePath.getParent());

        assert tasks != null : "Tasklist should not be null";

        ArrayList<String> lines = new ArrayList<>();
        for (Task task : tasks.asList()) {
            String line = formatLine(task);
            if (line != null) {
                lines.add(line);
            }
        }

        Files.write(filePath, lines);
    }

    /** Formats one task as a storage line, or returns {@code null} if its type is unrecognized. */
    private static String formatLine(Task task) {
        if (task instanceof Todo) {
            return TODO_TYPE + " | " + statusFor(task) + task.getDescription();
        } else if (task instanceof Deadline deadline) {
            return DEADLINE_TYPE + " | " + statusFor(task) + task.getDescription()
                    + " | " + deadline.getBy();
        } else if (task instanceof Event event) {
            return EVENT_TYPE + " | " + statusFor(task) + task.getDescription()
                    + " | " + event.getFrom() + " | " + event.getTo();
        }
        return null;
    }

    /** Returns the serialized completion status and its field separator. */
    private static String statusFor(Task task) {
        return (task.isDone() ? DONE_STATUS : NOT_DONE_STATUS) + " | ";
    }
}

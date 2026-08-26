import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Saves and loads Jelly tasks from local data file. */
public class Storage {
    /** Location of the tasks' data file, relative to project root. */
    private final Path filePath;

    /** Creates storage using a default data file. */
    public Storage() {
        this.filePath = Path.of("data", "jelly.txt");
    }

//    /**
//     * Loads tasks from the data file.
//     *
//     * @return the saved tasks, or an empty list if the file does not exist
//     * @throws IOException if the file cannot be read
//     */
//    public ArrayList<Task> load() throws IOException {
//        ArrayList<Task> tasks = new ArrayList<>();
//
//        if (!Files.exists(filePath)) {
//            return tasks;
//        }
//
//        List<String> lines = Files.readAllLines(filePath);
//
//        for (String line : lines) {
//            String[] parts = line.split(" \\| ");
//
//            if (parts.length < 3) {
//                continue;
//            }
//
//            String type = parts[0];
//            boolean isDone = parts[1].equals("1");
//            String description = parts[2];
//
//            Task task;
//
//            if (type.equals("T")) {
//                task = new Todo(description);
//            } else if (type.equals("D") && parts.length >= 4) {
//                task = new Deadline(description, parts[3]);
//            } else if (type.equals("E") && parts.length >= 5) {
//                task = new Event(description, parts[3], parts[4]);
//            } else {
//                continue;
//            }
//
//            if (isDone) {
//                task.markAsDone();
//            }
//
//            tasks.add(task);
//        }
//
//        return tasks;
//    }

    /**
     * Saves all tasks to the data file.
     *
     * @param tasks tasks to save
     * @throws IOException if the directory or file cannot be written
     */
    public void save(List<Task> tasks) throws IOException {
        Files.createDirectories(filePath.getParent());

        ArrayList<String> lines = new ArrayList<>();

        for (Task task : tasks) {
            String line;

            if (task instanceof Todo) {
                line = "T | " + (task.isDone ? "1 | " : "0 | ") + task.description;
            } else if (task instanceof Deadline deadline) {
                line = "D | " + (task.isDone ? "1 | " : "0 | ") + task.description
                        + " | " + deadline.by;
            } else if (task instanceof Event event) {
                line = "E | " + (task.isDone ? "1 | " : "0 | ") + task.description
                        + " | " + event.from + " | " + event.to;
            } else {
                continue;
            }

            lines.add(line);
        }

        Files.write(filePath, lines);
    }
}

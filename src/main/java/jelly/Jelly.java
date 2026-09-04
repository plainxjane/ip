package jelly;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

import jelly.command.CommandType;
import jelly.command.Parser;
import jelly.exception.JellyException;
import jelly.model.Deadline;
import jelly.model.Event;
import jelly.model.Task;
import jelly.model.TaskList;
import jelly.model.Todo;
import jelly.storage.Storage;
import jelly.ui.Ui;
import jelly.util.DateTimeParser;

/** Runs Jelly's command-line task manager. */
public class Jelly {

    private final TaskList tasks;
    private final Storage storage;
    private final Parser parser;
    private final Ui ui;

    /**
     * Creates a Jelly instance and loads its saved tasks.
     */
    public Jelly() {
        ui = new Ui();
        storage = new Storage();
        parser = new Parser();

        TaskList loadedTasks;
        try {
            loadedTasks = storage.load();
        } catch (IOException e) {
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
    }

    /**
     * Starts Jelly, reads commands from standard input, and updates the task list.
     *
     * @param args command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        TaskList tasks;
        Storage storage = new Storage();
        Parser parser = new Parser();

        try {
            tasks = storage.load();
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            CommandType commandType = parser.parse(command);

            try {
                if (commandType == CommandType.BYE) {
                    ui.showBye();
                    break;

                } else if (commandType == CommandType.LIST) {
                    ui.showTaskList(tasks);

                } else if (commandType == CommandType.FIND) {
                    String keyword = command.substring(4).trim();
                    if (keyword.isEmpty()) {
                        throw new JellyException("Please enter a keyword to find.");
                    }
                    ui.showMatchingTasks(tasks, keyword);

                } else if ((commandType == CommandType.MARK || commandType == CommandType.UNMARK)
                        && (command.equals("mark") || command.equals("unmark"))) {
                    throw new JellyException("Please enter a valid task number.");

                } else if (commandType == CommandType.MARK) {
                    int taskNumber;

                    try {
                        taskNumber = Integer.parseInt(command.substring(5).trim());
                    } catch (NumberFormatException e) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    if (taskNumber < 1 || taskNumber > tasks.size()) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    tasks.getTask(taskNumber - 1).markAsDone();
                    try {
                        storage.save(tasks);
                    } catch (IOException e) {
                        ui.showSavingError();
                    }

                    System.out.println("Nice! Jelly has marked this task as done~");
                    System.out.println("   [X] " + tasks.getTask(taskNumber - 1).getDescription());

                } else if (commandType == CommandType.UNMARK) {
                    int taskNumber;

                    try {
                        taskNumber = Integer.parseInt(command.substring(7).trim());
                    } catch (NumberFormatException e) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    if (taskNumber < 1 || taskNumber > tasks.size()) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    tasks.getTask(taskNumber - 1).markAsNotDone();
                    try {
                        storage.save(tasks);
                    } catch (IOException e) {
                        ui.showSavingError();
                    }

                    System.out.println("Ok, Jelly has marked this task as not done yet~");
                    System.out.println("   [ ] " + tasks.getTask(taskNumber - 1).getDescription());

                } else if (commandType == CommandType.TODO) {
                    String description = command.substring(4).trim();

                    if (description.isEmpty()) {
                        throw new JellyException("A Jelly to-do description cannot be empty!");
                    }


                    Todo todo = new Todo(description);
                    tasks.addTask(todo);
                    try {
                        storage.save(tasks);
                    } catch (IOException e) {
                        ui.showSavingError();
                    }

                    System.out.println("Got it! Jelly has added this task as a to-do:");
                    System.out.println("   " + todo);
                    System.out.println("\nNow you have " + tasks.size() + " tasks in your Jelly list~");

                } else if (commandType == CommandType.DEADLINE) {
                    if (!command.startsWith("deadline ")) {
                        throw new JellyException("A Jelly deadline needs a description and a /by date.");
                    }

                    String input = command.substring(9);
                    String[] parts = input.split(" /by ", 2);

                    if (parts.length < 2
                            || parts[0].trim().isEmpty()
                            || parts[1].trim().isEmpty()) {
                        throw new JellyException(
                                "Use: deadline <description> /by yyyy-mm-dd HHmm");
                    }

                    String description = parts[0].trim();
                    String by = parts[1].trim();
                    LocalDateTime dateTime;
                    try {
                        dateTime = DateTimeParser.parse(by);
                    } catch (DateTimeParseException e) {
                        throw new JellyException(
                                "Use a deadline date in the format yyyy-MM-dd HHmm, e.g. 2019-12-02 1800.");
                    }

                    Deadline deadline = new Deadline(description, dateTime);
                    tasks.addTask(deadline);
                    try {
                        storage.save(tasks);
                    } catch (IOException e) {
                        ui.showSavingError();
                    }

                    System.out.println("Got it! Jelly has added this task as a deadline:");
                    System.out.println("   " + deadline);
                    System.out.println("\nNow you have " + tasks.size() + " tasks in your Jelly list~");

                } else if (commandType == CommandType.EVENT) {
                    if (!command.startsWith("event ")) {
                        throw new JellyException("A Jelly event needs a description, start time, and end time.");
                    }

                    String input = command.substring(6).trim();
                    String[] parts = input.split(" /from ", 2);

                    if (parts.length < 2 || parts[0].trim().isEmpty()) {
                        throw new JellyException(
                                "Use: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm");
                    }

                    String[] times = parts[1].split(" /to ", 2);

                    if (times.length < 2
                            || times[0].trim().isEmpty()
                            || times[1].trim().isEmpty()) {
                        throw new JellyException(
                                "Use: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm");
                    }

                    String description = parts[0].trim();
                    String from = times[0].trim();
                    String to = times[1].trim();

                    LocalDateTime dateTimeFrom;
                    LocalDateTime dateTimeTo;

                    try {
                        dateTimeFrom = DateTimeParser.parse(from);
                        dateTimeTo = DateTimeParser.parse(to);
                    } catch (DateTimeParseException e) {
                        throw new JellyException(
                                "Use event dates in the format yyyy-MM-dd HHmm, e.g. 2019-12-02 1800.");
                    }

                    if (dateTimeTo.isBefore(dateTimeFrom)) {
                        throw new JellyException("An event's end time cannot be before its start time.");
                    }

                    Event event = new Event(description, dateTimeFrom, dateTimeTo);
                    tasks.addTask(event);
                    try {
                        storage.save(tasks);
                    } catch (IOException e) {
                        ui.showSavingError();
                    }

                    System.out.println("Got it! Jelly has added this task as an event:");
                    System.out.println("   " + event);
                    System.out.println("\nNow you have " + tasks.size() + " tasks in your Jelly list~");

                } else if (commandType == CommandType.DELETE && command.equals("delete")) {

                    throw new JellyException("Please enter a task number to delete.");

                } else if (commandType == CommandType.DELETE) {
                    int taskNumber;

                    try {
                        taskNumber = Integer.parseInt(command.substring(7).trim());
                    } catch (NumberFormatException e) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    if (taskNumber < 1 || taskNumber > tasks.size()) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    Task deletedTask = tasks.deleteTask(taskNumber - 1);
                    try {
                        storage.save(tasks);
                    } catch (IOException e) {
                        System.out.println("Jelly could not save your tasks.");
                    }

                    System.out.println("Congrats! Jelly has removed this task for you :)");
                    System.out.println(deletedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in your Jelly list~");

                } else {
                    throw new JellyException("Yikes! Jelly doesn't recognize that command. Try again~");
                }
            } catch (JellyException e) {
                ui.showError(e.getMessage());
            }
        }

    }

    /**
     * Processes one command entered by the user.
     *
     * @param command the command to process.
     * @return the response that should be displayed.
     */
    public String executeCommand(String command) {
        CommandType commandType = parser.parse(command);

        try {
            switch (commandType) {
                case LIST:
                    return ui.formatTaskList(tasks);
                case TODO:
                    return executeTodoCommand(command);
                case FIND:
                    return executeFindCommand(command);
                case MARK:
                    return executeMarkCommand(command, true);
                case UNMARK:
                    return executeMarkCommand(command, false);
                case DELETE:
                    return executeDeleteCommand(command);
                case DEADLINE:
                    return executeDeadlineCommand(command);
                case EVENT:
                    return executeEventCommand(command);
                case BYE:
                    return "Bye! Stay jiggly~";
                default:
                    throw new JellyException("Yikes! Jelly doesn't recognize that command. Try again~");
            }
        } catch (JellyException e) {
            return e.getMessage();
        }
    }

    /**
     * Creates and saves a to-do task.
     *
     * @param command the complete to-do command.
     * @return the response to display.
     * @throws JellyException if the description is empty.
     */
    private String executeTodoCommand(String command) throws JellyException {
        String description = command.substring(4).trim();

        if (description.isEmpty()) {
            throw new JellyException("A Jelly to-do description cannot be empty!");
        }

        Todo todo = new Todo(description);
        tasks.addTask(todo);

        StringBuilder response = new StringBuilder();
        response.append("Got it! Jelly has added this task as a to-do:\n");
        response.append("   ").append(todo);
        response.append("\n\nNow you have ")
                .append(tasks.size())
                .append(" tasks in your Jelly list~");

        try {
            storage.save(tasks);
        } catch (IOException e) {
            response.append("\n\nJelly could not save your tasks.");
        }

        return response.toString();
    }

    /** Finds tasks whose descriptions contain the requested keyword. */
    private String executeFindCommand(String command) throws JellyException {
        String keyword = command.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new JellyException("Please enter a keyword to find.");
        }

        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            if (task.getDescription().toLowerCase(Locale.ROOT).contains(normalizedKeyword)) {
                response.append(i + 1).append(".").append(task).append("\n");
            }
        }
        return response.toString().trim();
    }

    /** Marks or unmarks a task. */
    private String executeMarkCommand(String command, boolean mark) throws JellyException {
        String keyword = mark ? "mark" : "unmark";
        if (command.equals(keyword)) {
            throw new JellyException("Please enter a valid task number.");
        }

        int taskNumber = parseTaskNumber(command.substring(keyword.length()).trim());
        Task task = tasks.getTask(taskNumber - 1);
        if (mark) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
        saveTasks();

        String status = mark ? "[X]" : "[ ]";
        String message = mark ? "Nice! Jelly has marked this task as done~"
                : "Ok, Jelly has marked this task as not done yet~";
        return message + "\n   " + status + " " + task.getDescription();
    }

    /** Deletes a task. */
    private String executeDeleteCommand(String command) throws JellyException {
        if (command.equals("delete")) {
            throw new JellyException("Please enter a task number to delete.");
        }

        int taskNumber = parseTaskNumber(command.substring(7).trim());
        Task deletedTask = tasks.deleteTask(taskNumber - 1);
        saveTasks();
        return "Congrats! Jelly has removed this task for you :)\n" + deletedTask
                + "\nNow you have " + tasks.size() + " tasks in your Jelly list~";
    }

    /** Creates and saves a deadline task. */
    private String executeDeadlineCommand(String command) throws JellyException {
        if (!command.startsWith("deadline ")) {
            throw new JellyException("A Jelly deadline needs a description and a /by date.");
        }

        String[] parts = command.substring(9).split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new JellyException("Use: deadline <description> /by yyyy-mm-dd HHmm");
        }

        LocalDateTime dateTime = parseDate(parts[1].trim(),
                "Use a deadline date in the format yyyy-MM-dd HHmm, e.g. 2019-12-02 1800.");
        Deadline deadline = new Deadline(parts[0].trim(), dateTime);
        tasks.addTask(deadline);
        saveTasks();
        return "Got it! Jelly has added this task as a deadline:\n   " + deadline
                + "\n\nNow you have " + tasks.size() + " tasks in your Jelly list~";
    }

    /** Creates and saves an event task. */
    private String executeEventCommand(String command) throws JellyException {
        if (!command.startsWith("event ")) {
            throw new JellyException("A Jelly event needs a description, start time, and end time.");
        }

        String[] parts = command.substring(6).trim().split(" /from ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw new JellyException("Use: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm");
        }
        String[] times = parts[1].split(" /to ", 2);
        if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty()) {
            throw new JellyException("Use: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm");
        }

        LocalDateTime from = parseDate(times[0].trim(),
                "Use event dates in the format yyyy-MM-dd HHmm, e.g. 2019-12-02 1800.");
        LocalDateTime to = parseDate(times[1].trim(),
                "Use event dates in the format yyyy-MM-dd HHmm, e.g. 2019-12-02 1800.");
        if (to.isBefore(from)) {
            throw new JellyException("An event's end time cannot be before its start time.");
        }

        Event event = new Event(parts[0].trim(), from, to);
        tasks.addTask(event);
        saveTasks();
        return "Got it! Jelly has added this task as an event:\n   " + event
                + "\n\nNow you have " + tasks.size() + " tasks in your Jelly list~";
    }

    /** Parses and validates a one-based task number. */
    private int parseTaskNumber(String value) throws JellyException {
        try {
            int taskNumber = Integer.parseInt(value);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new JellyException("Please enter a valid task number.");
            }
            return taskNumber;
        } catch (NumberFormatException e) {
            throw new JellyException("Please enter a valid task number.");
        }
    }

    /** Parses a date and converts parsing failures into Jelly errors. */
    private LocalDateTime parseDate(String value, String errorMessage) throws JellyException {
        try {
            return DateTimeParser.parse(value);
        } catch (DateTimeParseException e) {
            throw new JellyException(errorMessage);
        }
    }

    /** Saves the current task list. */
    private void saveTasks() {
        try {
            storage.save(tasks);
        } catch (IOException e) {
            // The command is still completed in memory; the next save can retry.
        }
    }

}

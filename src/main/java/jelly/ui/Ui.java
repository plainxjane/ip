package jelly.ui;

import java.util.Locale;

import jelly.model.Task;
import jelly.model.TaskList;

/** Handles Jelly's interaction with the command-line user. */
public class Ui {
    /** Separates the task-list heading, entries, and footer. */
    private static final String TASK_SEPARATOR = "----------------------------------------------------";

    /** Separates the find-results heading, entries, and footer. */
    private static final String MATCH_SEPARATOR = "______________________________________________________";

    /** Prints Jelly's greeting and prompt. */
    public void showWelcome() {
        String banner = "╭──────────────────────╮\n"
                + "│      J E L L Y       │\n"
                + "│                      │\n"
                + "│       .-\"\"\"\"-.       │\n"
                + "│     .'  o  o  '.     │\n"
                + "│    /      ∆     \\    │\n"
                + "│    \\    '---'   /    │\n"
                + "│     '._      _.'     │\n"
                + "│        `----`        │\n"
                + "╰──────────────────────╯";

        System.out.println(banner);
        System.out.println("\nHello! I'm Jelly, your squishy little assistant!");
        System.out.println("What can I do for you? :)");
    }

    /** Prints the current tasks with their one-based list numbers. */
    public void showTaskList(TaskList tasks) {
        System.out.println(formatTaskList(tasks));
    }

    /**
     * Formats the current tasks with their one-based list numbers.
     *
     * @param tasks the task list to format.
     * @return the formatted task list.
     */
    public String formatTaskList(TaskList tasks) {
        StringBuilder output = new StringBuilder();

        output.append("Your Jelly Tasks :)\n");
        output.append(TASK_SEPARATOR).append("\n");

        for (int i = 0; i < tasks.size(); i++) {
            output.append(i + 1)
                    .append(".")
                    .append(tasks.getTask(i))
                    .append("\n");
        }

        output.append(TASK_SEPARATOR);

        return output.toString();
    }

    /** Prints tasks whose descriptions contain the supplied keyword. */
    public void showMatchingTasks(TaskList tasks, String keyword) {
        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        System.out.println(MATCH_SEPARATOR);
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            String normalizedDescription = task.getDescription().toLowerCase(Locale.ROOT);
            boolean isMatch = normalizedDescription.contains(normalizedKeyword);
            if (isMatch) {
                System.out.println((i + 1) + "." + task);
            }
        }
        System.out.println(MATCH_SEPARATOR);
    }

    /** Prints the standard formatted error message. */
    public void showError(String message) {
        System.out.println(MATCH_SEPARATOR);
        System.out.println(" " + message);
        System.out.println(MATCH_SEPARATOR);
    }

    /** Prints the message used when loading saved tasks fails. */
    public void showLoadingError() {
        System.out.println("Jelly could not load your saved tasks.");
        System.out.println("Jelly will start with an empty task list~");
    }

    /** Prints the message used when saving tasks fails. */
    public void showSavingError() {
        System.out.println("Jelly could not save your tasks.");
    }

    /** Prints Jelly's farewell. */
    public void showBye() {
        System.out.println("Bye! Stay jiggly~");
    }
}

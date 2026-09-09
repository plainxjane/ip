package jelly.ui;

import jelly.model.TaskList;

/** Handles Jelly's interaction with the command-line user. */
public class Ui {
    /** Jelly's farewell message, shared with {@code Jelly#computeCommandResult} for the GUI. */
    public static final String BYE_MESSAGE = "Bye! Stay jiggly~";

    /** Separates the task-list heading, entries, and footer. */
    private static final String TASK_SEPARATOR = "----------------------------------------------------";

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
        System.out.println(BYE_MESSAGE);
    }
}

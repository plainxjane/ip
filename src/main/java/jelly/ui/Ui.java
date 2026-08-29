package jelly.ui;

import jelly.model.Task;
import jelly.model.TaskList;

import java.util.Locale;

/** Handles Jelly's interaction with the command-line user. */
public class Ui {
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
        System.out.println("Your Jelly Tasks :)");
        System.out.println("----------------------------------------------------------");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.getTask(i));
        }
        System.out.println("----------------------------------------------------------");
    }

    /** Prints tasks whose descriptions contain the supplied keyword. */
    public void showMatchingTasks(TaskList tasks, String keyword) {
        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        System.out.println("____________________________________________________________");
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            if (task.getDescription().toLowerCase(Locale.ROOT).contains(normalizedKeyword)) {
                System.out.println((i + 1) + "." + task);
            }
        }
        System.out.println("____________________________________________________________");
    }

    /** Prints the standard formatted error message. */
    public void showError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + message);
        System.out.println("____________________________________________________________");
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

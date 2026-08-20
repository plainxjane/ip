import java.util.Scanner;
import java.util.ArrayList;

/** Runs Jelly's command-line task manager. */
public class Jelly {
    /**
     * Starts Jelly, reads commands from standard input, and updates the task list.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
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

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            CommandType commandType = getCommandType(command);

            try {

                if (commandType == CommandType.BYE) {
                    System.out.println("Bye! Stay jiggly~");
                    break;

                } else if (commandType == CommandType.LIST) {
                    System.out.println("Your Jelly Tasks :)");
                    System.out.println("----------------------------------------------------------");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    System.out.println("----------------------------------------------------------");

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

                    // mark task as done
                    tasks.get(taskNumber - 1).markAsDone();

                    System.out.println("Nice! Jelly has marked this task as done~");
                    System.out.println("   [X] " + tasks.get(taskNumber - 1).getDescription());

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

                    // mark task as undone
                    tasks.get(taskNumber - 1).markAsNotDone();

                    System.out.println("Ok, Jelly has marked this task as not done yet~");
                    System.out.println("   [ ] " + tasks.get(taskNumber - 1).getDescription());

                } else if (commandType == CommandType.TODO) {
                    String description = command.substring(4).trim();

                    if (description.isEmpty()) {
                        throw new JellyException("A Jelly to-do description cannot be empty!");
                    }


                    Todo todo = new Todo(description);
                    tasks.add(todo);

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
                                "Use: deadline <description> /by <date>");
                    }

                    String description = parts[0].trim();
                    String by = parts[1].trim();

                    Deadline deadline = new Deadline(description, by);
                    tasks.add(deadline);

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
                                "Use: event <description> /from <start> /to <end>");
                    }

                    String[] times = parts[1].split(" /to ", 2);

                    if (times.length < 2
                            || times[0].trim().isEmpty()
                            || times[1].trim().isEmpty()) {
                        throw new JellyException(
                                "Use: event <description> /from <start> /to <end>");
                    }

                    String description = parts[0].trim();
                    String from = times[0].trim();
                    String to = times[1].trim();

                    Event event = new Event(description, from, to);
                    tasks.add(event);

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

                    Task deletedTask = tasks.remove(taskNumber - 1);

                    System.out.println("Congrats! Jelly has removed this task for you :)");
                    System.out.println(deletedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in your Jelly list~");

                } else {
                    throw new JellyException("Yikes! Jelly doesn't recognize that command. Try again~");
                }
            } catch (JellyException e) {
                showError(e.getMessage());
            }
        }

    }

    /**
     * Identifies the command represented by an input line.
     *
     * @param command the raw command entered by the user
     * @return the matching command type, or {@link CommandType#INVALID}
     */
    private static CommandType getCommandType(String command) {
        if (command.equals("bye")) {
            return CommandType.BYE;
        } else if (command.equals("list")) {
            return CommandType.LIST;
        } else if (command.equals("todo") || command.startsWith("todo ")) {
            return CommandType.TODO;
        } else if (command.equals("deadline") || command.startsWith("deadline ")) {
            return CommandType.DEADLINE;
        } else if (command.equals("event") || command.startsWith("event ")) {
            return CommandType.EVENT;
        } else if (command.equals("mark") || command.startsWith("mark ")) {
            return CommandType.MARK;
        } else if (command.equals("unmark") || command.startsWith("unmark ")) {
            return CommandType.UNMARK;
        } else if (command.equals("delete") || command.startsWith("delete ")) {
            return CommandType.DELETE;
        }
        return CommandType.INVALID;
    }

    /**
     * Prints an error message between Jelly's standard divider lines.
     *
     * @param message the error message to display
     */
    private static void showError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + message);
        System.out.println("____________________________________________________________");
    }
}

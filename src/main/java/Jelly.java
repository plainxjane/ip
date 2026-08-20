import java.util.Scanner;

public class Jelly {
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
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            try {

                if (command.equals("bye")) {
                    System.out.println("Bye! Stay jiggly~");
                    break;

                } else if (command.equals("list")) {
                    System.out.println("Your Jelly Tasks :)");
                    System.out.println("----------------------------------------------------------");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                    System.out.println("----------------------------------------------------------");

                } else if (command.equals("mark") || command.equals("unmark")) {
                    throw new JellyException("Please enter a valid task number.");

                } else if (command.startsWith("mark ")) {
                    int taskNumber;

                    try {
                        taskNumber = Integer.parseInt(command.substring(5).trim());
                    } catch (NumberFormatException e) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    if (taskNumber < 1 || taskNumber > taskCount) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    // mark task as done
                    tasks[taskNumber - 1].markAsDone();

                    System.out.println("Nice! Jelly has marked this task as done~");
                    System.out.println("   [X] " + tasks[taskNumber - 1].getDescription());

                } else if (command.startsWith("unmark ")) {
                    int taskNumber;

                    try {
                        taskNumber = Integer.parseInt(command.substring(7).trim());
                    } catch (NumberFormatException e) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    if (taskNumber < 1 || taskNumber > taskCount) {
                        throw new JellyException("Please enter a valid task number.");
                    }

                    // mark task as undone
                    tasks[taskNumber - 1].markAsNotDone();

                    System.out.println("Ok, Jelly has marked this task as not done yet~");
                    System.out.println("   [ ] " + tasks[taskNumber - 1].getDescription());

                } else if (command.equals("todo") || command.startsWith("todo ")) {
                    String description = command.substring(4).trim();

                    if (description.isEmpty()) {
                        throw new JellyException("A Jelly to-do description cannot be empty!");
                    }

                    if (taskCount >= tasks.length) {
                        throw new JellyException("Jelly's task list is full :(");
                    }

                    tasks[taskCount] = new Todo(description);
                    taskCount++;

                    System.out.println("Got it! Jelly has added this task as a to-do:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println("\nNow you have " + taskCount + " tasks in your Jelly list~");

                } else if (command.startsWith("deadline")) {
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

                    if (taskCount >= tasks.length) {
                        throw new JellyException("Jelly's task list is full :(");
                    }

                    tasks[taskCount] = new Deadline(description, by);
                    taskCount++;

                    System.out.println("Got it! Jelly has added this task as a deadline:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println("\nNow you have " + taskCount + " tasks in your Jelly list~");

                } else if (command.startsWith("event")) {
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

                    if (taskCount >= tasks.length) {
                        throw new JellyException("Jelly's task list is full :(");
                    }

                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;

                    System.out.println("Got it! Jelly has added this task as an event:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println("\nNow you have " + taskCount + " tasks in your Jelly list~");

                } else {
                    throw new JellyException("Yikes! Jelly doesn't recognize that command. Try again~");
                }
            } catch (JellyException e) {
                showError(e.getMessage());
            }
        }

    }

    // helper method to print error message
    private static void showError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + message);
        System.out.println("____________________________________________________________");
    }
}

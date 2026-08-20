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

            if (command.equals("bye")) {
                System.out.println("Bye! Stay jiggly~");
                break;

            } else if (command.equals("list")) {
                System.out.println("Your Jelly Tasks :)");
                System.out.println("----------------------------------------------------------");
                for (int i = 0; i < taskCount; i++) {
                    String taskStatus = tasks[i].getStatusIcon();
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                System.out.println("----------------------------------------------------------");

            } else if (command.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(command.substring(5));
                int taskIndex = taskNumber - 1;

                // mark task as done
                tasks[taskIndex].markAsDone();

                System.out.println("Nice! Jelly has marked this task as done~");
                System.out.println("   [X] " + tasks[taskIndex].getDescription());

            } else if (command.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(command.substring(7));
                int taskIndex = taskNumber - 1;

                // mark task as undone
                tasks[taskIndex].markAsNotDone();

                System.out.println("Ok, Jelly has marked this task as not done yet~");
                System.out.println("   [ ] " + tasks[taskIndex].getDescription());

            } else if (command.startsWith("todo")) {
                String description = command.substring(5);

                tasks[taskCount] = new Task(description);
                taskCount++;

                System.out.println("Got it! Jelly has added this task as a to-do:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in your Jelly list~");

            } else if (command.startsWith("deadline")) {
                String input = command.substring(9);
                String[] parts = input.split(" /by ", 2);

                String description = parts[0];
                String by = parts[1];

                tasks[taskCount] = new Task(description, by);
                taskCount++;

                System.out.println("Got it! Jelly has added this task as a deadline:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in your Jelly list~");

            } else if (command.startsWith("event")) {
                String input = command.substring(6);
                String [] parts = input.split(" /from ", 2);

                String description = parts[0];
                String[] times = parts[1].split(" /to ", 2);
                String from = times[0];
                String to = times[1];

                tasks[taskCount] = new Task(description, from, to);
                taskCount++;

                System.out.println("Got it! Jelly has added this task as an event:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in your Jelly list~");

            } else {
                tasks[taskCount] = new Task(command);
                taskCount++;
                System.out.println("Added to Jelly: " + command);
            }
        }
    }
}

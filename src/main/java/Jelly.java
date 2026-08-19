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
        String[] tasks = new String[100];
        int taskCount = 0;
        boolean[] taskDone = new boolean[100];

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println("Bye! Stay jiggly~");
                break;

            } else if (command.equals("list")) {
                System.out.println("Your Jelly Tasks :)");
                System.out.println("----------------------------------------------------------");
                for (int i = 0; i < taskCount; i++) {
                    String taskStatus = taskDone[i] ? "X" : " ";
                    System.out.println((i + 1) + ".[" + taskStatus + "] " + tasks[i]);
                }
                System.out.println("----------------------------------------------------------");

            } else if (command.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(command.substring(5));
                int taskIndex = taskNumber - 1;

                // mark task as done
                taskDone[taskIndex] = true;

                System.out.println("Nice! Jelly has marked this task as done~");
                System.out.println("   [X] " + tasks[taskIndex]);

            } else if (command.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(command.substring(7));
                int taskIndex = taskNumber - 1;

                // mark task as undone
                taskDone[taskIndex] = false;

                System.out.println("Ok, Jelly has marked this task as not done yet~");
                System.out.println("   [] " + tasks[taskIndex]);

            } else {
                tasks[taskCount] = command;
                taskDone[taskCount] = false;
                taskCount++;
                System.out.println("Added to Jelly: " + command);
            }
        }
    }
}

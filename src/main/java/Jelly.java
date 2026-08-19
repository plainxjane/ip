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

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println("Bye! Stay jiggly~");
                break;
            } else if (command.equals("list")) {
                System.out.println("Your Jelly Tasks :)");
                System.out.println("----------------------------------------------------------");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println("----------------------------------------------------------");
            } else {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println("Added to Jelly: " + command);
            }
        }
    }
}

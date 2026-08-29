package jelly.command;

/** Converts raw user input into a recognized Jelly command type. */
public class Parser {
    /** Identifies the command represented by an input line. */
    public CommandType parse(String command) {
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
        } else if (command.equals("find") || command.startsWith("find ")) {
            return CommandType.FIND;
        }
        return CommandType.INVALID;
    }
}

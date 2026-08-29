package jelly.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Tests command recognition performed by {@link Parser}. */
class ParserTest {
    private final Parser parser = new Parser();

    @Test
    void parse_exactCommand_returnsExpectedCommandType() {
        assertEquals(CommandType.BYE, parser.parse("bye"));
        assertEquals(CommandType.LIST, parser.parse("list"));
        assertEquals(CommandType.TODO, parser.parse("todo"));
        assertEquals(CommandType.DEADLINE, parser.parse("deadline"));
        assertEquals(CommandType.EVENT, parser.parse("event"));
        assertEquals(CommandType.MARK, parser.parse("mark"));
        assertEquals(CommandType.UNMARK, parser.parse("unmark"));
        assertEquals(CommandType.DELETE, parser.parse("delete"));
    }

    @Test
    void parse_commandWithDescription_returnsExpectedCommandType() {
        assertEquals(CommandType.TODO, parser.parse("todo buy groceries"));
        assertEquals(CommandType.DEADLINE, parser.parse("deadline submit report"));
        assertEquals(CommandType.EVENT, parser.parse("event team meeting"));
        assertEquals(CommandType.MARK, parser.parse("mark 1"));
        assertEquals(CommandType.UNMARK, parser.parse("unmark 1"));
        assertEquals(CommandType.DELETE, parser.parse("delete 1"));
    }

    @Test
    void parse_invalidCommand_returnsInvalidCommandType() {
        assertEquals(CommandType.INVALID, parser.parse(""));
        assertEquals(CommandType.INVALID, parser.parse("blah"));
        assertEquals(CommandType.INVALID, parser.parse("today"));
        assertEquals(CommandType.INVALID, parser.parse("todoSomething"));
        assertEquals(CommandType.INVALID, parser.parse("deadlineSomething"));
        assertEquals(CommandType.INVALID, parser.parse("eventSomething"));
    }
}

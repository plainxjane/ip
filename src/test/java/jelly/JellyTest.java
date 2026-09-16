package jelly;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JellyTest {

    @Test
    void executeCommand_invalidTaskDescription_returnsError() {
        Jelly jelly = new Jelly();

        String response = jelly.executeCommand("todo bad | data");

        assertTrue(jelly.wasLastResponseAnError());
        assertTrue(response.contains("valid command"));
    }
}

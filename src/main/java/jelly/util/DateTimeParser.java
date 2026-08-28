package jelly.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/** Converts user-entered date/time text into date-times. */
public class DateTimeParser {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm");

    private DateTimeParser() {
        // Utility class; do not instantiate.
    }

    /** Parses the format entered by a user. */
    public static LocalDateTime parse(String date) {
        try {
            return LocalDateTime.parse(date, INPUT_FORMATTER);
        } catch (DateTimeParseException exception) {
            // LocalDateTime's default parser accepts its ISO format used by jelly.txt.
            return LocalDateTime.parse(date);
        }
    }
}

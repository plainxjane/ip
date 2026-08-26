import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/** Converts deadline text between Jelly's input/storage formats and date-times. */
public class DateTimeParser {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm");
    private static final DateTimeFormatter STORAGE_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private DateTimeParser() {
        // Utility class; do not instantiate.
    }

    /** Parses the format entered by a user. */
    public static LocalDateTime parse(String date) {
        try {
            return LocalDateTime.parse(date, INPUT_FORMATTER);
        } catch (DateTimeParseException exception) {
            // Also accept the ISO format already written in older jelly.txt files.
            return LocalDateTime.parse(date, STORAGE_FORMATTER);
        }
    }
}

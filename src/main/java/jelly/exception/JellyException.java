package jelly.exception;

/** Represents an error caused by invalid or unsuccessful Jelly operations. */
public class JellyException extends Exception {

    /**
     * Creates an exception describing an error specific to Jelly.
     *
     * @param message the explanation of the error
     */
    public JellyException(String message) {
        super(message);
    }
}

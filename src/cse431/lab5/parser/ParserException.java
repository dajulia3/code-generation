package cse431.lab5.parser;

/**
 * An exception for parser errors.
 */
public class ParserException extends Exception {
    private static final long serialVersionUID = 726147768988453466L;

    /**
     * Constructs a ParserException with no detail message.
     */
    public ParserException() {
        super();
    }

    /**
     * Constructs a ParserException with the specified detail message.
     * 
     * @param message
     *            the detail message.
     */
    public ParserException(String message) {
        super(message);
    }

    /**
     * Constructs a ParserException with the specified cause.
     * 
     * @param cause
     *            the cause.
     */
    public ParserException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a ParserException with the specified detail message and cause.
     * 
     * @param message
     *            the detail message.
     * @param cause
     *            the cause.
     */
    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}

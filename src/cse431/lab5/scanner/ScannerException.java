package cse431.lab5.scanner;

/**
 * An exception for scanner errors.
 */
public class ScannerException extends Exception {
    private static final long serialVersionUID = 8734041402465786380L;

    /**
     * Constructs a ScannerException with no detail message.
     */
    public ScannerException() {
        super();
    }

    /**
     * Constructs a ScannerException with the specified detail message.
     * 
     * @param message
     *            the detail message.
     */
    public ScannerException(String message) {
        super(message);
    }

    /**
     * Constructs a ScannerException with the specified cause.
     * 
     * @param cause
     *            the cause.
     */
    public ScannerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a ScannerException with the specified detail message and
     * cause.
     * 
     * @param message
     *            the detail message.
     * @param cause
     *            the cause.
     */
    public ScannerException(String message, Throwable cause) {
        super(message, cause);
    }
}

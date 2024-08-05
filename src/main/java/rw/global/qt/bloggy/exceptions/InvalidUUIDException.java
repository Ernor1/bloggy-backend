package rw.global.qt.bloggy.exceptions;

public class InvalidUUIDException extends RuntimeException{
    public InvalidUUIDException(String message) {
        super(message);
    }

    public InvalidUUIDException(String message, Throwable cause) {
        super(message, cause);
    }
}

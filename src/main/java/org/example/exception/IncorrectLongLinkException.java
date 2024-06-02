package org.example.exception;

public class IncorrectLongLinkException extends RuntimeException {

    public IncorrectLongLinkException() {
    }

    public IncorrectLongLinkException(String message) {
        super(message);
    }

}

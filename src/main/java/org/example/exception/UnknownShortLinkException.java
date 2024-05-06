package org.example.exception;

public class UnknownShortLinkException extends Exception {

    public UnknownShortLinkException() {
    }

    public UnknownShortLinkException(String message) {
        super(message);
    }

}
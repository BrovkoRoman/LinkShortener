package org.example.exception;

public class BadRepositoryFunctionCallException extends RuntimeException {
    public BadRepositoryFunctionCallException() {}

    public BadRepositoryFunctionCallException(String message) {super(message);}
}

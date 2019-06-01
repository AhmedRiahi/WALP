package com.ef.exception;

public class InvalidCommandLineArgumentException extends RuntimeException {

    public InvalidCommandLineArgumentException(String argName) {
        super(argName);
    }
}

package com.ef.exception;

public class UnknownCommandLineArgumentException extends RuntimeException{

    public UnknownCommandLineArgumentException(String argumentName){
        super("Unrecognized argument name :"+argumentName);
    }
}

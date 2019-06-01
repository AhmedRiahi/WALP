package com.ef.exception;

public class UnknownArgumentException extends RuntimeException{

    public UnknownArgumentException(String argumentName){
        super("Unrecognized argument name :"+argumentName);
    }
}

package com.ef.domain.exception;

public class FileLoadingException extends RuntimeException {

    public FileLoadingException(Exception e){
        super(e);
    }
}

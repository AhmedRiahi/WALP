package com.ef.exception;

import com.ef.helper.CommandLineArgumentsHelper;

import java.util.List;

public class MissingCommandLineArgumentsException extends RuntimeException {

    public MissingCommandLineArgumentsException(List<CommandLineArgumentsHelper.ArgumentKey> missingArgs){
        super(missingArgs.toString());
    }
}

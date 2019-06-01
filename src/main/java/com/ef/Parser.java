package com.ef;

import com.ef.helper.CommandLineArgumentsHelper;

import java.util.Map;

public class Parser {

    public static void main(String[] args){
        System.out.println("Launching Parser ...");
        Map<CommandLineArgumentsHelper.ArgumentKey,String> argsMap = CommandLineArgumentsHelper.parseArgs(args);

    }
}

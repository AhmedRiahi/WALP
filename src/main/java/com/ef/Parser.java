package com.ef;

import com.ef.helper.ParserArgumentsHelper;

import java.util.Map;

public class Parser {

    public static void main(String[] args){
        System.out.println("Launching Parser ...");
        Map<ParserArgumentsHelper.ArgumentKey,String> argsMap = ParserArgumentsHelper.parseArgs(args);

    }
}

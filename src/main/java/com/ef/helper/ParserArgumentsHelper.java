package com.ef.helper;

import com.ef.exception.UnknownArgumentException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParserArgumentsHelper {

    public enum ArgumentKey{
        ACCESS_LOG("accesslog"),
        START_DATE("startDate"),
        DURATION("duration"),
        THRESHOLD("threshold");

        private String name;

        ArgumentKey(String name){
            this.name = name;
        }


        public static ArgumentKey find(String key){
            return Arrays.asList(ArgumentKey.values())
                    .stream()
                    .filter(argumentKey -> argumentKey.name.equals(key))
                    .findFirst()
                    .orElseThrow(() -> new UnknownArgumentException(key));
        }
    }

    public static Map<ArgumentKey,String> parseArgs(String[] args){
        Map<ArgumentKey,String> argsMap = new HashMap<>();
        for(String arg : args){
            String[] keyValue = arg.split("=", 2);
            argsMap.put(ArgumentKey.find(keyValue[0]),keyValue[1]);
        }
        return argsMap;
    }
}

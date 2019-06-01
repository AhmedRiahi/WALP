package com.ef.helper;

import com.ef.exception.InvalidCommandLineArgumentException;
import com.ef.exception.MissingCommandLineArgumentsException;
import com.ef.exception.UnknownArgumentException;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
public class CommandLineArgumentsHelper {

    public static final String DATE_FORMAT = "yyyy-MM-dd.HH:mm:ss";

    public enum ArgumentKey {
        ACCESS_LOG("accesslog"),
        START_DATE("startDate"),
        DURATION("duration"),
        THRESHOLD("threshold");

        private String name;

        ArgumentKey(String name) {
            this.name = name;
        }


        public static ArgumentKey find(String key) {
            return Arrays.asList(ArgumentKey.values())
                    .stream()
                    .filter(argumentKey -> argumentKey.name.equals(key))
                    .findFirst()
                    .orElseThrow(() -> new UnknownArgumentException(key));
        }
    }

    public static Map<ArgumentKey, Object> parseArgs(String[] args) {
        Map<ArgumentKey, Object> argsMap = new HashMap<>();
        for (String arg : args) {
            String[] keyValue = arg.split("=", 2);
            argsMap.put(ArgumentKey.find(keyValue[0].substring(2)), keyValue[1]);
        }
        CommandLineArgumentsHelper.validateArgs(argsMap);
        return argsMap;
    }

    public static void validateArgs(Map<ArgumentKey, Object> argsMap) {
        List<ArgumentKey> missingArgs = Arrays.asList(ArgumentKey.values())
                .stream()
                .filter(argumentKey -> !argsMap.containsKey(argumentKey))
                .collect(Collectors.toList());
        if (!missingArgs.isEmpty()) {
            throw new MissingCommandLineArgumentsException(missingArgs);
        }

        try {
            String accessLog = argsMap.get(ArgumentKey.ACCESS_LOG).toString();
            argsMap.put(ArgumentKey.ACCESS_LOG, Paths.get(accessLog));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InvalidCommandLineArgumentException(ArgumentKey.ACCESS_LOG.name);
        }

        try {
            String startDate = argsMap.get(ArgumentKey.START_DATE).toString();
            argsMap.put(ArgumentKey.START_DATE, DateHelper.toLocalDateTime(startDate, CommandLineArgumentsHelper.DATE_FORMAT));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InvalidCommandLineArgumentException(ArgumentKey.START_DATE.name);
        }


        if (!argsMap.get(ArgumentKey.DURATION).toString().equals("hourly") && !argsMap.get(ArgumentKey.DURATION).toString().equals("daily")) {
            throw new InvalidCommandLineArgumentException(ArgumentKey.DURATION.name);
        }


        try {
            String threshold = argsMap.get(ArgumentKey.THRESHOLD).toString();
            argsMap.put(ArgumentKey.THRESHOLD, Integer.parseInt(threshold));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InvalidCommandLineArgumentException(ArgumentKey.START_DATE.name);
        }

    }
}
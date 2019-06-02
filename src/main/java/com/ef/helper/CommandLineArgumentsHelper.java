package com.ef.helper;

import com.ef.config.ParserConfig;
import com.ef.exception.InvalidCommandLineArgumentException;
import com.ef.exception.MissingCommandLineArgumentsException;
import com.ef.exception.UnknownCommandLineArgumentException;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
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
                    .orElseThrow(() -> new UnknownCommandLineArgumentException(key));
        }
    }

    public static ParserConfig parseArgs(String[] args) {
        EnumMap<ArgumentKey, Object> argsMap = new EnumMap<>(ArgumentKey.class);
        for (String arg : args) {
            String[] keyValue = arg.split("=", 2);
            argsMap.put(ArgumentKey.find(keyValue[0].substring(2)), keyValue[1]);
        }
        CommandLineArgumentsHelper.validateArgs(argsMap);
        return CommandLineArgumentsHelper.createParserConfig(argsMap);
    }

    public static void validateArgs(EnumMap<ArgumentKey, Object> argsMap) {
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
            throw new InvalidCommandLineArgumentException(ArgumentKey.THRESHOLD.name);
        }
    }


    public static ParserConfig createParserConfig(EnumMap<ArgumentKey,Object> argsMap){
        LocalDateTime startDate = (LocalDateTime)argsMap.get(CommandLineArgumentsHelper.ArgumentKey.START_DATE);
        LocalDateTime endDate = null;
        if (argsMap.get(CommandLineArgumentsHelper.ArgumentKey.DURATION).toString().equals("hourly")){
            endDate = startDate.plusHours(1);
        }else if (argsMap.get(CommandLineArgumentsHelper.ArgumentKey.DURATION).toString().equals("daily")){
            endDate = startDate.plusDays(1);
        }
        endDate = endDate.minusSeconds(1).withNano(999999999);

        return ParserConfig.builder()
                .logFilePath((Path)argsMap.get(CommandLineArgumentsHelper.ArgumentKey.ACCESS_LOG))
                .startDate(startDate)
                .endDate(endDate)
                .threshold((Integer)argsMap.get(ArgumentKey.THRESHOLD))
        .build();
    }

}

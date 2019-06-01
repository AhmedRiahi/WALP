package com.ef.domain.service;

import com.ef.domain.bean.LogLineBean;
import com.ef.domain.exception.FileLoadingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileParser {

    private int linesNumberChunck = 1000;
    private String logLineSeparator = "|";


    public List<LogLineBean> load(String filePath){
        List<LogLineBean> logLineBeans = new ArrayList<>(this.linesNumberChunck);
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream.map(this::transform).collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw new FileLoadingException(e);
        }
    }


    private LogLineBean transform(String logLine){
        String[] logLineSplitted = logLine.split(this.logLineSeparator);
        return LogLineBean.builder().date(LocalDateTime.parse(logLineSplitted[0])).ip(logLineSplitted[1]).build();
    }
}

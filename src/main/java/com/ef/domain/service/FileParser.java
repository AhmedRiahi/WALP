package com.ef.domain.service;

import com.ef.domain.bean.LogLineBean;
import com.ef.domain.exception.FileLoadingException;
import com.ef.helper.DateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileParser {

    @Value("${parser.log.date.format}")
    public String dateFormat;

    @Value("${parser.log.separator}")
    private String logLineSeparator;


    public List<LogLineBean> load(Path filePath) {
        try (Stream<String> stream = Files.lines(filePath)) {
            return stream.map(this::transform).collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileLoadingException(e);
        }
    }


    private LogLineBean transform(String logLine) {
        String[] logLineSplitted = logLine.split(this.logLineSeparator);
        return LogLineBean.builder().dateTime(DateHelper.toLocalDateTime(logLineSplitted[0], dateFormat)).ip(logLineSplitted[1]).build();
    }
}

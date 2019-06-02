package com.ef.domain.service;

import com.ef.domain.bean.LogLineBean;
import com.ef.persistence.repository.LogLineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class WorkflowExecutor {

    @Autowired
    private FileParser fileParser;
    @Autowired
    private LogLinesRangeFetcher logLinesRangeFetcher;

    @Autowired
    private LogLineRepository logLineRepository;

    public void launch(Path filePath, LocalDateTime startDate,LocalDateTime endDate){
        log.info("Launching workflow");
        List<LogLineBean> logLineBeans = this.fileParser.load(filePath);
        log.info("found "+logLineBeans.size()+" log line(s)");
        List<LogLineBean> logLinesRange = this.logLinesRangeFetcher.fetchRange(logLineBeans,startDate,endDate);
        log.info("found "+logLinesRange.size()+" log line(s) after filtering");

        log.info("Finished workflow execution");

    }
}

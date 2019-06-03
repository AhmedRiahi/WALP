package com.ef.domain.service;

import com.ef.config.ParserConfig;
import com.ef.domain.bean.LogLineBean;
import com.ef.persistence.entity.LogLineEntity;
import com.ef.persistence.repository.LogLineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Service
@Slf4j
public class WorkflowExecutor {

    @Value("${parser.full-load}")
    private boolean fullLoad;

    @Autowired
    private FileParser fileParser;

    @Autowired
    private LogLinesRangeFetcher logLinesRangeFetcher;

    @Autowired
    private IPAddressAnalyser ipAddressAnalyser;

    @Autowired
    private IPBlocker ipBlocker;

    @Autowired
    private LogLineRepository logLineRepository;

    public void launch(ParserConfig parserConfig) {
        log.info("Launching workflow");

        List<LogLineBean> logLineBeans = this.fileParser.load(parserConfig.getLogFilePath());
        log.info("found " + logLineBeans.size() + " log line(s)");

        if(this.fullLoad){
            log.info("Persisting all log line(s)");
            this.persistAllLogLines(logLineBeans);
        }

        List<LogLineBean> logLinesRange = this.logLinesRangeFetcher.fetchRange(logLineBeans, parserConfig.getStartDate(), parserConfig.getEndDate());
        log.info("found " + logLinesRange.size() + " log line(s) after filtering");

        List<String> suspiciousIps = this.ipAddressAnalyser.analyse(logLineBeans, parserConfig.getThreshold());
        log.info("found " + suspiciousIps.size() + " suspicious Ip(s)");
        log.info("List of suspicious Ip(s):");
        log.info(suspiciousIps.stream().collect(Collectors.joining("\n")));
        this.ipBlocker.block(suspiciousIps, parserConfig);
        log.info("Finished workflow execution");
    }


    public void persistAllLogLines(List<LogLineBean> logLineBeans) {
        AtomicLong counter = new AtomicLong(1);
        List<LogLineEntity> logLineEntities = logLineBeans.stream().map(logLineBean -> {
            LogLineEntity logLineEntity = new LogLineEntity();
            logLineEntity.setId(counter.incrementAndGet());
            BeanUtils.copyProperties(logLineBean, logLineEntity);
            return logLineEntity;
        }).collect(Collectors.toList());
        this.logLineRepository.saveAll(logLineEntities);
    }
}

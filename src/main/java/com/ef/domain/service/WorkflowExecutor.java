package com.ef.domain.service;

import com.ef.config.ParserConfig;
import com.ef.domain.bean.LogLineBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class WorkflowExecutor {

    @Autowired
    private FileParser fileParser;
    @Autowired
    private LogLinesRangeFetcher logLinesRangeFetcher;
    @Autowired
    private IPAddressAnalyser ipAddressAnalyser;
    @Autowired
    private IPBlocker ipBlocker;

    public void launch(ParserConfig parserConfig){
        log.info("Launching workflow");
        List<LogLineBean> logLineBeans = this.fileParser.load(parserConfig.getLogFilePath());
        log.info("found "+logLineBeans.size()+" log line(s)");
        List<LogLineBean> logLinesRange = this.logLinesRangeFetcher.fetchRange(logLineBeans,parserConfig.getStartDate(),parserConfig.getEndDate());
        log.info("found "+logLinesRange.size()+" log line(s) after filtering");
        List<String> suspiciousIps = this.ipAddressAnalyser.analyse(logLineBeans,parserConfig.getThreshold());
        log.info("found "+suspiciousIps.size()+" suspicious Ips");
        this.ipBlocker.block(suspiciousIps);
        log.info("Finished workflow execution");
    }
}

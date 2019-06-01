package com.ef.domain.service;

import com.ef.domain.bean.LogLineBean;
import com.ef.persistence.entity.LogLineEntity;
import com.ef.persistence.repository.LogLineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class WorkflowExecutor {

    @Autowired
    private FileParser fileParser;

    @Autowired
    private LogLineRepository logLineRepository;

    public void launch(String filePath){
        log.info("Launching workflow");
        List<LogLineBean> logLineBeans = this.fileParser.load(filePath);
        this.persistLogLines(logLineBeans);

        log.info("Finished workflow execution");

    }

    private void persistLogLines(List<LogLineBean> logLineBeans){
        log.info("Saving log lines "+logLineBeans.size()+" lines");
        List<LogLineEntity> logLineEntities = logLineBeans.stream().map(logLineBean -> {
            LogLineEntity logLineEntity = new LogLineEntity();
            BeanUtils.copyProperties(logLineBean,logLineEntity);
            return logLineEntity;
        }).collect(Collectors.toList());
        this.logLineRepository.saveAll(logLineEntities);
    }
}

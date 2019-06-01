package com.ef.domain.service;

import com.ef.domain.bean.LogLineBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WorkflowExecutor {

    @Autowired
    private FileParser fileParser;

    public void launch(String filePath){
        List<LogLineBean> logLineBeans = this.fileParser.load(filePath);
    }
}

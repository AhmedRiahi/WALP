package com.ef;

import com.ef.config.ParserConfig;
import com.ef.domain.service.WorkflowExecutor;
import com.ef.helper.CommandLineArgumentsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class Parser {

    public static void main(String[] args){
        log.info("Launching Parser ...");
        log.info("Parsing args ...");
        ParserConfig parserConfig = CommandLineArgumentsHelper.parseArgs(args);
        log.info("Configuration :"+parserConfig);
        ConfigurableApplicationContext context = SpringApplication.run(Parser.class,args);
        WorkflowExecutor workflowExecutor = context.getBean(WorkflowExecutor.class);
        workflowExecutor.launch(parserConfig);
        context.registerShutdownHook();
    }
}

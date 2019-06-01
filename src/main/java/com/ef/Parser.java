package com.ef;

import com.ef.domain.service.WorkflowExecutor;
import com.ef.helper.CommandLineArgumentsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
@Slf4j
public class Parser {

    public static void main(String[] args){
        log.info("Launching Parser ...");
        Map<CommandLineArgumentsHelper.ArgumentKey,Object> argsMap = CommandLineArgumentsHelper.parseArgs(args);
        ConfigurableApplicationContext context = SpringApplication.run(Parser.class,args);
        WorkflowExecutor workflowExecutor = context.getBean(WorkflowExecutor.class);
        workflowExecutor.launch(argsMap.get(CommandLineArgumentsHelper.ArgumentKey.ACCESS_LOG).toString());
        context.registerShutdownHook();
    }
}

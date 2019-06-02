package com.ef;

import com.ef.domain.service.WorkflowExecutor;
import com.ef.helper.CommandLineArgumentsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class Parser {

    public static void main(String[] args){
        log.info("Launching Parser ...");
        Map<CommandLineArgumentsHelper.ArgumentKey,Object> argsMap = CommandLineArgumentsHelper.parseArgs(args);
        ConfigurableApplicationContext context = SpringApplication.run(Parser.class,args);
        WorkflowExecutor workflowExecutor = context.getBean(WorkflowExecutor.class);
        Path filePath = (Path)argsMap.get(CommandLineArgumentsHelper.ArgumentKey.ACCESS_LOG);
        LocalDateTime startDate = (LocalDateTime)argsMap.get(CommandLineArgumentsHelper.ArgumentKey.START_DATE);
        LocalDateTime endDate = null;
        switch (argsMap.get(CommandLineArgumentsHelper.ArgumentKey.DURATION).toString()){
            case "hourly":endDate = startDate.plusHours(1); break;
            case "daily":endDate = startDate.plusDays(1); break;
        }
        endDate = endDate.minusSeconds(1);
        int threshold = (Integer)argsMap.get(CommandLineArgumentsHelper.ArgumentKey.THRESHOLD);
        workflowExecutor.launch(filePath,startDate,endDate,threshold);
        context.registerShutdownHook();
    }
}

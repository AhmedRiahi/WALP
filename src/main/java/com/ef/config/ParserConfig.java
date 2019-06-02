package com.ef.config;


import lombok.*;

import java.nio.file.Path;

import java.time.LocalDateTime;

@Data
@Builder
public class ParserConfig {

    private Path logFilePath;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int threshold;

}

package com.ef.domain.bean;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LogLineBean {

    private LocalDateTime dateTime;
    private String ip;
}

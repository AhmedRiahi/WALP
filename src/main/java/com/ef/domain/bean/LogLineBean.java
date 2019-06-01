package com.ef.domain.bean;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class LogLineBean {

    private LocalDateTime date;
    private String ip;
}

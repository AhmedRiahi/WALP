package com.ef.domain.service;

import com.ef.config.ParserConfig;
import com.ef.persistence.entity.BlockedIP;
import com.ef.persistence.repository.BlockedIPRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IPBlocker {

    @Autowired
    private BlockedIPRepository blockedIPRepository;

    public void block(List<String> suspiciousIps, ParserConfig parserConfig){
        log.info("start persisting suspicious IPs");
        String blockComment = "More than "+parserConfig.getThreshold()+" between "+parserConfig.getStartDate()+" and "+parserConfig.getEndDate();
        final AtomicLong id = new AtomicLong(1);
        List<BlockedIP> blockedIPS = suspiciousIps.stream()
                .map(ip -> BlockedIP.builder()
                        .id(id.incrementAndGet())
                        .ip(ip)
                        .comment(blockComment)
                        .build())
                .collect(Collectors.toList());
        this.blockedIPRepository.saveAll(blockedIPS);
        log.info("finished persisting suspicious IPs");
    }
}

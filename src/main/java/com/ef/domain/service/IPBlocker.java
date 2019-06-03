package com.ef.domain.service;

import com.ef.persistence.entity.BlockedIP;
import com.ef.persistence.repository.BlockedIPRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IPBlocker {

    @Autowired
    private BlockedIPRepository blockedIPRepository;

    public void block(List<String> suspiciousIps){
        log.info("start persisting suspicious IPs");
        List<BlockedIP> blockedIPS = suspiciousIps.stream().map(ip -> BlockedIP.builder().ip(ip).build()).collect(Collectors.toList());
        this.blockedIPRepository.saveAll(blockedIPS);
        log.info("finished persisting suspicious IPs");
    }
}

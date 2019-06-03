package com.ef.domain.service;


import com.ef.domain.bean.LogLineBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IPAddressAnalyser {

    public List<String> analyse(List<LogLineBean> logLineBeans,int threshold){
        Map<String,Long> ipsCount = this.generateCountingMap(logLineBeans);
        return ipsCount.keySet().stream().filter(ip -> ipsCount.get(ip) >= threshold).collect(Collectors.toList());
    }


    private Map<String,Long> generateCountingMap(List<LogLineBean> logLineBeans){
        return logLineBeans.stream().map(LogLineBean::getIp).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
    }
}

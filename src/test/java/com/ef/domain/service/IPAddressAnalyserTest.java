package com.ef.domain.service;


import com.ef.domain.bean.LogLineBean;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IPAddressAnalyserTest {

    @Autowired
    private IPAddressAnalyser ipAddressAnalyser;

    @Test
    public void should_generate_suspicious_ips(){
        List<String> suspiciousIps = this.ipAddressAnalyser.analyse(this.createLogLineBeans(),5);
        Assertions.assertThat(suspiciousIps).contains("1.1.1.1");
        Assertions.assertThat(suspiciousIps.size()).isEqualTo(1);

        suspiciousIps = this.ipAddressAnalyser.analyse(this.createLogLineBeans(),3);
        Assertions.assertThat(suspiciousIps).contains("1.1.1.1");
        Assertions.assertThat(suspiciousIps).contains("1.1.1.2");
        Assertions.assertThat(suspiciousIps.size()).isEqualTo(2);

        suspiciousIps = this.ipAddressAnalyser.analyse(this.createLogLineBeans(),6);
        Assertions.assertThat(suspiciousIps.size()).isEqualTo(0);
    }

    public List<LogLineBean> createLogLineBeans(){
        List<LogLineBean> logLineBeans = new ArrayList<>();
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.1").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.1").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.1").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.1").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.1").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.2").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.2").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.2").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.3").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.3").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.4").build());
        logLineBeans.add(LogLineBean.builder().ip("1.1.1.5").build());
        return logLineBeans;
    }
}

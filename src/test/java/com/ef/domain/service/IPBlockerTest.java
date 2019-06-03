package com.ef.domain.service;

import com.ef.config.ParserConfig;
import com.ef.persistence.repository.BlockedIPRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IPBlockerTest {

    @Autowired
    private IPBlocker ipBlocker;

    @Autowired
    private BlockedIPRepository blockedIPRepository;

    @Test
    public void given_list_of_suspicious_ips_when_calling_blocker_ip_should_be_persisted_on_database(){
        int beforeSize = this.blockedIPRepository.findAll().size();
        this.ipBlocker.block(Arrays.asList("1.1.1.1","1.1.1.2","1.1.1.3"), ParserConfig.builder().build());
        int afterSize = this.blockedIPRepository.findAll().size();
        Assertions.assertThat(afterSize - beforeSize).isEqualTo(3);
    }

}

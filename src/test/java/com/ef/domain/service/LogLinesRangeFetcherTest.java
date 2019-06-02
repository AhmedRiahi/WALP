package com.ef.domain.service;


import com.ef.domain.bean.LogLineBean;
import com.ef.helper.DateHelper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogLinesRangeFetcherTest {


    @Autowired
    private LogLinesRangeFetcher logLinesRangeFetcher;

    @Test
    public void should_return_correct_range(){

        //middle range test
        LocalDateTime startDate = DateHelper.toLocalDateTime("2017-02-01.14:00:00","yyyy-MM-dd.HH:mm:ss");
        LocalDateTime endDate = DateHelper.toLocalDateTime("2017-02-01.16:59:03","yyyy-MM-dd.HH:mm:ss");
        List<LogLineBean> logLineBeans = this.logLinesRangeFetcher.fetchRange(this.createLogLineBeans(),startDate,endDate);
        Assertions.assertThat(logLineBeans.size()).isEqualTo(6);

        //lower extreme range test
        startDate = DateHelper.toLocalDateTime("2017-02-01.00:00:00","yyyy-MM-dd.HH:mm:ss");
        endDate = DateHelper.toLocalDateTime("2017-02-01.16:59:03","yyyy-MM-dd.HH:mm:ss");
        logLineBeans = this.logLinesRangeFetcher.fetchRange(this.createLogLineBeans(),startDate,endDate);
        Assertions.assertThat(logLineBeans.size()).isEqualTo(8);

        //upper extreme range test
        startDate = DateHelper.toLocalDateTime("2017-02-01.14:00:00","yyyy-MM-dd.HH:mm:ss");
        endDate = DateHelper.toLocalDateTime("2017-02-01.20:59:03","yyyy-MM-dd.HH:mm:ss");
        logLineBeans = this.logLinesRangeFetcher.fetchRange(this.createLogLineBeans(),startDate,endDate);
        Assertions.assertThat(logLineBeans.size()).isEqualTo(10);

        // out of scope range test
        startDate = DateHelper.toLocalDateTime("2017-03-01.14:00:00","yyyy-MM-dd.HH:mm:ss");
        endDate = DateHelper.toLocalDateTime("2017-03-01.20:59:03","yyyy-MM-dd.HH:mm:ss");
        logLineBeans = this.logLinesRangeFetcher.fetchRange(this.createLogLineBeans(),startDate,endDate);
        Assertions.assertThat(logLineBeans.size()).isEqualTo(0);

        //all inside range test
        startDate = DateHelper.toLocalDateTime("2017-02-01.10:00:00","yyyy-MM-dd.HH:mm:ss");
        endDate = DateHelper.toLocalDateTime("2017-03-01.20:59:03","yyyy-MM-dd.HH:mm:ss");
        logLineBeans = this.logLinesRangeFetcher.fetchRange(this.createLogLineBeans(),startDate,endDate);
        Assertions.assertThat(logLineBeans.size()).isEqualTo(12);
    }


    public List<LogLineBean> createLogLineBeans(){
        List<LogLineBean> logLineBeans = new ArrayList<>();
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.13:05:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.13:25:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.14:05:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.14:25:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.15:05:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.15:25:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.16:05:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.16:25:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.17:05:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.17:25:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.18:05:03","yyyy-MM-dd.HH:mm:ss")).build());
        logLineBeans.add(LogLineBean.builder().dateTime(DateHelper.toLocalDateTime("2017-02-01.18:25:03","yyyy-MM-dd.HH:mm:ss")).build());
        return logLineBeans;
    }
}

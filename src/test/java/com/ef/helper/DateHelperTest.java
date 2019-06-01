package com.ef.helper;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
public class DateHelperTest {

    @Test
    public void given_known_date_pattern_then_should_parse_date_correctly(){
        LocalDateTime localDateTime = DateHelper.toLocalDateTime("2017-02-01.13:05:03","yyyy-MM-dd.HH:mm:ss");
        Assertions.assertThat(localDateTime.getYear()).isEqualTo(2017);
        Assertions.assertThat(localDateTime.getMonthValue()).isEqualTo(2);
        Assertions.assertThat(localDateTime.getDayOfMonth()).isEqualTo(1);
        Assertions.assertThat(localDateTime.getHour()).isEqualTo(13);
        Assertions.assertThat(localDateTime.getMinute()).isEqualTo(5);
        Assertions.assertThat(localDateTime.getSecond()).isEqualTo(3);
    }


    @Test
    public void given_unknown_date_pattern_then_should_parse_date_correctly(){
        LocalDateTime localDateTime = DateHelper.toLocalDateTime("2017-02-01.13:05:03.478","yyyy-MM-dd.HH:mm:ss.SSS");
        Assertions.assertThat(localDateTime.getYear()).isEqualTo(2017);
        Assertions.assertThat(localDateTime.getMonthValue()).isEqualTo(2);
        Assertions.assertThat(localDateTime.getDayOfMonth()).isEqualTo(1);
        Assertions.assertThat(localDateTime.getHour()).isEqualTo(13);
        Assertions.assertThat(localDateTime.getMinute()).isEqualTo(5);
        Assertions.assertThat(localDateTime.getSecond()).isEqualTo(3);
        Assertions.assertThat(localDateTime.getNano()).isEqualTo(478000000);
    }
}

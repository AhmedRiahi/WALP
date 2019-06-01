package com.ef;

import com.ef.exception.MissingCommandLineArgumentsException;
import com.ef.exception.UnknownArgumentException;
import com.ef.helper.CommandLineArgumentsHelper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
public class CommandLineArgumentsHelperTest {

    @Test(expected = UnknownArgumentException.class)
    public void should_throw_exception_for_unknown_argument(){
        String[] args = new String[]{"--unknowArgs=value"};
        CommandLineArgumentsHelper.parseArgs(args);
    }

    @Test(expected = MissingCommandLineArgumentsException.class)
    public void should_throw_exception_for_missing_argument(){
        String[] args = new String[]{"--accesslog=test.log"};
        CommandLineArgumentsHelper.parseArgs(args);
    }

    @Test
    public void should_parse_argument_correctly(){
        String[] args = new String[]{"--accesslog=/path/to/file","--startDate=2017-01-01.13:00:00","--duration=hourly","--threshold=100"};
        Map<CommandLineArgumentsHelper.ArgumentKey,String> argsMap = CommandLineArgumentsHelper.parseArgs(args);
        Assertions.assertThat(argsMap.get(CommandLineArgumentsHelper.ArgumentKey.ACCESS_LOG)).isEqualTo("/path/to/file");
        Assertions.assertThat(argsMap.get(CommandLineArgumentsHelper.ArgumentKey.START_DATE)).isEqualTo("2017-01-01.13:00:00");
        Assertions.assertThat(argsMap.get(CommandLineArgumentsHelper.ArgumentKey.DURATION)).isEqualTo("hourly");
        Assertions.assertThat(argsMap.get(CommandLineArgumentsHelper.ArgumentKey.THRESHOLD)).isEqualTo("100");
    }
}

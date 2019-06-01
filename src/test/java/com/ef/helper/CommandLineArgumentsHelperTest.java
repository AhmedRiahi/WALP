package com.ef.helper;

import com.ef.exception.InvalidCommandLineArgumentException;
import com.ef.exception.MissingCommandLineArgumentsException;
import com.ef.exception.UnknownArgumentException;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Paths;
import java.util.Map;

@RunWith(SpringRunner.class)
public class CommandLineArgumentsHelperTest {

    @Test(expected = UnknownArgumentException.class)
    public void should_throw_exception_for_unknown_argument() {
        String[] args = new String[]{"--unknowArgs=value"};
        CommandLineArgumentsHelper.parseArgs(args);
    }

    @Test(expected = MissingCommandLineArgumentsException.class)
    public void should_throw_exception_for_missing_argument() {
        String[] args = new String[]{"--accesslog=test.log"};
        CommandLineArgumentsHelper.parseArgs(args);
    }

    @Test(expected = InvalidCommandLineArgumentException.class)
    public void should_throw_exception_for_invalid_startDate_argument() {
        String[] args = new String[]{"--accesslog=invalid_path.log", "--startDate=not_a_date", "--duration=hourly", "--threshold=100"};
        CommandLineArgumentsHelper.parseArgs(args);
    }

    @Test(expected = InvalidCommandLineArgumentException.class)
    public void should_throw_exception_for_invalid_duration_argument() {
        String[] args = new String[]{"--accesslog=invalid_path.log", "--startDate=2017-01-01.13:00:00", "--duration=invalid", "--threshold=100"};
        CommandLineArgumentsHelper.parseArgs(args);
    }

    @Test(expected = InvalidCommandLineArgumentException.class)
    public void should_throw_exception_for_invalid_threshold_argument() {
        String[] args = new String[]{"--accesslog=invalid_path.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=xxx"};
        CommandLineArgumentsHelper.parseArgs(args);
    }

    @Test
    public void should_parse_argument_correctly() {
        String[] args = new String[]{"--accesslog=/path/to/file", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};
        Map<CommandLineArgumentsHelper.ArgumentKey, Object> argsMap = CommandLineArgumentsHelper.parseArgs(args);
        Assertions.assertThat(argsMap.get(CommandLineArgumentsHelper.ArgumentKey.ACCESS_LOG)).isEqualTo(Paths.get("/path/to/file"));
        Assertions.assertThat(argsMap.get(CommandLineArgumentsHelper.ArgumentKey.START_DATE)).isEqualTo(DateHelper.toLocalDateTime("2017-01-01.13:00:00",CommandLineArgumentsHelper.DATE_FORMAT));
        Assertions.assertThat(argsMap.get(CommandLineArgumentsHelper.ArgumentKey.DURATION)).isEqualTo("hourly");
        Assertions.assertThat(argsMap.get(CommandLineArgumentsHelper.ArgumentKey.THRESHOLD)).isEqualTo(100);
    }
}

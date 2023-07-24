package geektime.tdd.args;

import geektime.tdd.args.exceptions.InsufficientArgumentsException;
import geektime.tdd.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.function.Function;

import static geektime.tdd.args.BooleanOptionParserTest.option;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class SingleValuedOptionParserTest {

    @Test // sad path
    public void should_not_accept_extra_argument_for_single_valued_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValueOptionParser<>(0, Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    @ParameterizedTest // sad path
    @ValueSource(strings = {"-p -l", "-p"})
    public void should_accept_insufficient_argument_for_single_valued_option(String arguments) {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () -> {
            new SingleValueOptionParser<>(0, Integer::parseInt).parse(List.of(arguments.split(" ")), option("p"));
        });
    }

    @Test // default value
    public void should_set_default_value_to_0_for_int_option() {
        Function<String,Object> whatever = (it) -> null;
        Object defaultValue = new Object();

        assertSame(defaultValue, new SingleValueOptionParser<>(defaultValue, whatever).parse(List.of(), option("p")));
    }

    @Test // happy path
    public void should_parse_value_if_flag_present() {
        Object parsed = new Object();
        Function<String, Object> parse = (it) -> parsed;
        Object whatEver = new Object();

        assertSame(parsed, new SingleValueOptionParser<>(whatEver, parse).parse(List.of("-p", "8080"), option("p")));
    }


}

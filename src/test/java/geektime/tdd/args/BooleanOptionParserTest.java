package geektime.tdd.args;

import geektime.tdd.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class BooleanOptionParserTest {

    @Test // Sad path
    public void should_not_accept_extra_arguments_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                                                   () -> BooleanOptionParser.bool()
                                                                            .parse(asList("-l", "t"),
                                                                                   option("l")));
        assertEquals("l", e.getOption());
    }


    @Test // Default value
    public void should_set_value_to_true_if_option_present() {
        assertFalse(BooleanOptionParser.bool().parse(List.of(), option("l")));
    }

    @Test // Happy Path
    public void should_set_default_value_if_option_not_present() {
        assertTrue(BooleanOptionParser.bool().parse(List.of("-l"), option("l")));
    }


    public static Option option(String value) {
        return new Option() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }
}

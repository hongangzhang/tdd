package geektime.tdd.args;

import geektime.tdd.args.exceptions.IllegalValueException;
import geektime.tdd.args.exceptions.InsufficientArgumentsException;
import geektime.tdd.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class OptionParsersTest {

    @Nested
    class UnaryOptionParser {
        @Test // sad path
        public void should_not_accept_extra_argument_for_single_valued_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                OptionParsers.unary(0, Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p"));
            });

            assertEquals("p", e.getOption());
        }

        @ParameterizedTest // sad path
        @ValueSource(strings = {"-p -l", "-p"})
        public void should_accept_insufficient_argument_for_single_valued_option(String arguments) {
            InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () -> {
                OptionParsers.unary(0, Integer::parseInt)
                             .parse(List.of(arguments.split(" ")), option("p"));
            });
        }

        @Test // default value
        public void should_set_default_value_to_0_for_int_option() {
            Function<String, Object> whatever = (it) -> null;
            Object defaultValue = new Object();

            assertSame(defaultValue, OptionParsers.unary(defaultValue, whatever)
                                                  .parse(List.of(), option("p")));
        }

        @Test // happy path
        public void should_parse_value_if_flag_present() {
            Object parsed = new Object();
            Function<String, Object> parse = (it) -> parsed;
            Object whatEver = new Object();

            assertSame(parsed, OptionParsers.unary(whatEver, parse)
                                            .parse(List.of("-p", "8080"), option("p")));
        }

    }

    @Nested
    class BooleanOptionParser {

        @Test // Sad path
        public void should_not_accept_extra_arguments_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                                                       () -> OptionParsers.bool()
                                                                          .parse(asList("-l", "t"),
                                                                                       option("l")));
            assertEquals("l", e.getOption());
        }


        @Test // Default value
        public void should_set_default_value_to_false_if_option_not_present() {
            assertTrue(OptionParsers.bool().parse(List.of("-l"), option("l")));
        }

        @Test // Happy Path
        public void should_set_value_to_true_if_option_present() {
            assertFalse(OptionParsers.bool().parse(List.of(), option("l")));
        }

    }

    @Nested
    class ListOptionParser {
        @Test
        public void should_parse_list_value() {
            assertArrayEquals(new String[]{"this", "is"}, OptionParsers.list(String[]::new, String::valueOf).parse(asList("-g", "this", "is"), option("g")));
        }

        @Test
        public void should_not_treat_negative_int_as_flag () {
            assertArrayEquals(new Integer[]{-1, -2}, OptionParsers.list(Integer[]::new, Integer::parseInt).parse(asList("-g", "-1", "-2"), option("g")));
        }

        @Test
        public void should_use_empty_array_as_default_value() {
            String[] value = OptionParsers.list(String[]::new, String::valueOf).parse(List.of(), option("g"));
            assertEquals(0, value.length);
        }

        @Test
        public void should_throw_exception_if_value_parser_cant_parse_value() {
            Function<String, String> parser = (it) -> {throw  new RuntimeException();};
            IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                    OptionParsers.list(String[]::new, parser).parse(asList("-g", "this", "is"), option("g")));
            assertEquals("g", e.getOption());
            assertEquals("this", e.getValue());
        }
    }

    static Option option(String value) {
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

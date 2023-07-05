package geektime.tdd.args;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static geektime.tdd.args.BooleanOptionParserTest.option;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValuedOptionParserTest {

    @Test
    public void should_not_accept_extra_argument_for_single_valued_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValueOptionParser<>(Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p"));
        });

        Assertions.assertEquals("p", e.getOption());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-p -l", "-p"})
    public void should_accept_insufficient_argument_for_single_valued_option(String arguments) {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () -> {
            new SingleValueOptionParser<>(Integer::parseInt).parse(List.of(arguments.split(" ")), option("p"));
        });
}

// TODO: - string -d \ -d /usr/logs /usr/vars
// default value:
// TODO: - int : 0
    @Test
    public void should_set_default_value_to_0_for_int_option(){
//        assertEquals(0, new SingleValueOptionParser<>(Integer::parseInt).parse(List.of(), option("p")));
    }
// TODO: - string : ""

}

package geektime.tdd.args;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BooleanOptionParserTest {

    // sad path:
    @Test
    public void should_not_accept_extra_arguments_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new BooleanOptionParser().parse(asList("-l", "t"), option("l"));
        });

        assertEquals("l", e.getOption());
    }

    // default value:
    @Test
    public void should_set_default_value_if_option_not_present(){
        Assertions.assertFalse(new BooleanOptionParser().parse(asList(), option("l")));
    }


    private Option option(String value) {
        return new Option() {

            /**
             * Returns the annotation interface of this annotation.
             *
             * @return the annotation interface of this annotation
             * @apiNote Implementation-dependent classes are used to provide
             * the implementations of annotations. Therefore, calling {@link
             * Object#getClass getClass} on an annotation will return an
             * implementation-dependent class. In contrast, this method will
             * reliably return the annotation interface of the annotation.
             * @see Enum#getDeclaringClass
             */
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

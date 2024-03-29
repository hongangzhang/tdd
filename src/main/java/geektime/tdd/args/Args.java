package geektime.tdd.args;

import geektime.tdd.args.exceptions.IllegalOptonException;
import geektime.tdd.args.exceptions.UnsupportedOptionTypeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {

    private static final Map<Class<?>, OptionParser> PARSERS =
            Map.of(boolean.class, OptionParsers.bool(),
                   int.class, OptionParsers.unary(0, Integer::parseInt),
                   String.class, OptionParsers.unary("", String::valueOf),
                   String[].class, OptionParsers.list(String[]::new, String::valueOf),
                   Integer[].class, OptionParsers.list(Integer[]::new, Integer::parseInt)
                  );

    public static <T> T parse(Class<T> optionsClass, String... args) {

        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
            Object[] values = Arrays.stream(constructor.getParameters())
                                    .map(it -> parseOption(arguments, it))
                                    .toArray();

            return (T) constructor.newInstance(values);
        } catch (IllegalOptonException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        if (!parameter.isAnnotationPresent(Option.class)) {
            throw new IllegalOptonException(parameter.getName());
        }

        Option option = parameter.getAnnotation(Option.class);
        if (!PARSERS.containsKey(parameter.getType())) {
            throw new UnsupportedOptionTypeException(option.value(), parameter.getType());
        }

        return PARSERS.get(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

}

package geektime.tdd.args;

import geektime.tdd.args.exceptions.IllegalOptonException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {

    private static final Map<Class<?>, OptionParser> PARSERS =
            Map.of(boolean.class, SingleValueOptionParser.bool(),
                   int.class, SingleValueOptionParser.unary(0, Integer::parseInt),
                   String.class, SingleValueOptionParser.unary("", String::valueOf));

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
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        if (!parameter.isAnnotationPresent(Option.class)) {
            throw new IllegalOptonException(parameter.getName());
        }

        return PARSERS.get(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

}

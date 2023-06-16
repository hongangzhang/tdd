package geektime.tdd.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {

        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor =
                    optionsClass.getDeclaredConstructors()[0];
            Object[] values =
                    Arrays.stream(constructor.getParameters()).map(it ->
                            parseOption(arguments, it)).toArray();

            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Option option = parameter.getAnnotation(Option.class);

        OptionParser parser = null;

        if (parameter.getType().equals(boolean.class)) {
            parser = new BooleanOptionParser();
        }

        if (parameter.getType().equals(int.class)) {
            parser = new IntOptionParser();
        }

        if (parameter.getType().equals(String.class)) {
            parser = new StringOptionParser();
        }

        return parser.parse(arguments, option);

    }

    interface OptionParser {
        Object parse(List<String> arguments, Option option);
    }

    static class StringOptionParser implements OptionParser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            return arguments.get(index + 1);
        }
    }

    static class IntOptionParser implements OptionParser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            return Integer.parseInt(arguments.get(index + 1));
        }
    }

    static class BooleanOptionParser implements  OptionParser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            return arguments.contains("-" + option.value());
        }
    }

}

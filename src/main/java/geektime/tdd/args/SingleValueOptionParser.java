package geektime.tdd.args;

import geektime.tdd.args.exceptions.IllegalValueException;
import geektime.tdd.args.exceptions.InsufficientArgumentsException;
import geektime.tdd.args.exceptions.TooManyArgumentsException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

class SingleValueOptionParser<T> implements OptionParser<T> {

    Function<String, T> valueParser;

    T defaultValue;

    public SingleValueOptionParser(T defaultValue, Function<String, T> valueParser) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) throws IllegalValueException {
        int index = arguments.indexOf("-" + option.value());

        if (index == -1) {
            return defaultValue;
        }

        int followingFlag = IntStream.range(index + 1, arguments.size())
                                     .filter(it -> arguments.get(it).startsWith("-"))
                                     .findFirst()
                                     .orElse(arguments.size());

        List<String> values = arguments.subList(index + 1, followingFlag);

        if (values.size() < 1) {
            throw new InsufficientArgumentsException(option.value());
        }

        if (values.size() > 1) {
            throw new TooManyArgumentsException(option.value());
        }

        String value = arguments.get(index + 1);
        try {
            return valueParser.apply(value);
        } catch (Exception e) {
            throw new IllegalValueException(option.value(), value);
        }
    }

}

package geektime.tdd.args;

import java.util.List;

import static geektime.tdd.args.SingleValueOptionParser.values;

class BooleanOptionParser implements OptionParser<Boolean> {

    private BooleanOptionParser() {
    }

    public static OptionParser<Boolean> createBooleanOptionParser() {
        return (arguments, option) -> values(arguments, option, 0)
                .map(it -> true).orElse(false);
    }

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return values(arguments, option, 0)
                .map(it -> true).orElse(false);
    }
}

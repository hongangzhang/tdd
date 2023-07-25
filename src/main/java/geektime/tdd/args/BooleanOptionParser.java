package geektime.tdd.args;

import java.util.List;

import static geektime.tdd.args.SingleValueOptionParser.values;

class BooleanOptionParser implements OptionParser<Boolean> {

    public BooleanOptionParser() {
    }

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return values(arguments, option, 0)
                .map(it -> true).orElse(false);
    }
}

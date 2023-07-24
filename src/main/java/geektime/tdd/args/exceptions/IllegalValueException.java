package geektime.tdd.args.exceptions;

public class IllegalValueException extends RuntimeException{

    private final String optionValue;

    private final String parameter;

    public IllegalValueException(String optionValue, String parameter) {
        super(parameter);
        this.optionValue = optionValue;
        this.parameter = parameter;
    }


    public String getOptionValue() {
        return optionValue;
    }

    public String getParameter() {
        return parameter;
    }

}

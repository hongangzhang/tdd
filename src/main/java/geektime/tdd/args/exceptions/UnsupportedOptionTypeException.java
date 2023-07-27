package geektime.tdd.args.exceptions;

public class UnsupportedOptionTypeException extends RuntimeException{

    private final String value;

    private final Class<?> type;

    public UnsupportedOptionTypeException(String value, Class<?> type) {
        this.value = value;
        this.type = type;
    }


    public String getValue() {
        return value;
    }

    public Class<?> getType() {
        return type;
    }

}

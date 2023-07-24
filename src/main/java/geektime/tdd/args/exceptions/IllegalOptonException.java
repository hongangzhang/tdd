package geektime.tdd.args.exceptions;

public class IllegalOptonException extends RuntimeException{

    private final String parameter;

    public IllegalOptonException(String parameter) {
        super(parameter);
        this.parameter = parameter;
    }


    public String getParameter() {
        return parameter;
    }

}

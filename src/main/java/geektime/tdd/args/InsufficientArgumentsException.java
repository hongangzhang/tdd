package geektime.tdd.args;

public class InsufficientArgumentsException extends RuntimeException{

    private final String option;

    public InsufficientArgumentsException(String option) {
        super(option);
        this.option = option;
    }


    public String getOption() {
        return option;
    }

}

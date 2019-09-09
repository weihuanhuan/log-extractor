package parser;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class ParserException extends Exception {

    public ParserException(String message) {
        super(message);
    }

    public ParserException(Throwable throwable) {
        super(throwable);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}

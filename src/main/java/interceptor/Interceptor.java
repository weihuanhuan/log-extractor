package interceptor;

import entry.LogRecord;
import result.Result;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public interface Interceptor {

    void invoke(LogRecord logRecord);

    Interceptor getNext();

    void setNext(Interceptor interceptor);

    void setResult(Result result);

    Result getResult();
}

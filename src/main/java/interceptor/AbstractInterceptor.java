package interceptor;

import entry.LogRecord;
import result.Result;

abstract public class AbstractInterceptor implements Interceptor {

    protected Interceptor next;

    protected Result result;

    @Override
    abstract public void invoke(LogRecord logRecord);

    @Override
    public Interceptor getNext() {
        return this.next;
    }

    @Override
    public void setNext(Interceptor interceptor) {
        this.next = interceptor;
    }

    @Override
    public void setResult(Result result) {
        this.result =result;
    }

    @Override
    public Result getResult() {
        return result;
    }


}

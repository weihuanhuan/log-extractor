package interceptor;

import entry.LogRecord;
import result.Result;
import result.StatisticResult;

/**
 * Created by JasonFitch on 9/12/2019.
 */
public class ExceptionInfoInterceptor extends AbstractInterceptor {

    public ExceptionInfoInterceptor(Result result) {
        this.result = result;
    }

    @Override
    public void invoke(LogRecord logRecord) {
        StatisticResult exceptionResult = (StatisticResult) this.result;
        exceptionResult.getExceptionList().add(logRecord);
        exceptionResult.getAccumulator().count(logRecord.getDetail());

    }
}

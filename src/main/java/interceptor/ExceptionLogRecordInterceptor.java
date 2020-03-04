package interceptor;

import entry.LogRecord;
import result.Result;
import result.StatisticResult;

/**
 * Created by JasonFitch on 9/12/2019.
 */
public class ExceptionLogRecordInterceptor extends AbstractInterceptor {

    public ExceptionLogRecordInterceptor(Result result) {
        this.result = result;
    }

    @Override
    public void invoke(LogRecord logRecord) {
        StatisticResult exceptionResult = (StatisticResult) this.result;
        exceptionResult.getExceptionList().add(logRecord);

        String detail = logRecord.getDetail();
        int indexOf = detail.indexOf(":");
        if (indexOf > 0) {
            String exceptionName = detail.substring(0, indexOf);
            String exceptionInfo = detail.substring(indexOf);
            exceptionResult.getAccumulator().count(exceptionName, exceptionInfo);
        } else {
            exceptionResult.getAccumulator().count(detail);
        }
    }
}

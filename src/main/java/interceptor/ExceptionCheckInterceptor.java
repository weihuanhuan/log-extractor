package interceptor;

import entry.LogRecord;
import result.Result;
import result.StatisticResult;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class ExceptionCheckInterceptor extends AbstractInterceptor implements Interceptor {

    private int matchLength;

    public ExceptionCheckInterceptor() {
        matchLength = -1;
    }

    public ExceptionCheckInterceptor(int matchLength, Result result) {
        this.matchLength = matchLength;
        this.result = result;
    }

    @Override
    public void invoke(LogRecord logRecord) {
        StatisticResult statisticResult = (StatisticResult) this.result;

        //日志自身判断自己是否是异常日志
        if (logRecord.checkHasException()) {
            statisticResult.getExceptionList().add(logRecord);

            //统计重复的异常数量
            if (matchLength >= 0) {
                String detail = logRecord.getDetail();
                if (detail.length() <= matchLength) {
                    //小于匹配长度的按照全量来匹配
                    statisticResult.getAccumulator().count(detail);
                } else {
                    statisticResult.getAccumulator().count(detail.substring(0, matchLength));
                }
            }
        }

        if (this.next != null) {
            this.next.invoke(logRecord);
        }
    }

}

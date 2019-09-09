package entry;

import matcher.LogMatcher;
import util.Constants;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class BESLogRecord extends AbstractLogRecord {

    //4
    private String threadInfo;

    public BESLogRecord() {
        threadInfo = Constants.EMPTY_STRING;
    }

    public BESLogRecord(String time, String level, String module, String threadInfo, String detail) {
        super(time, level, module, detail);
        this.threadInfo = threadInfo;
    }

    @Override
    public boolean checkHasException() {
        return LogMatcher.printMatchesWholeEntry(getDetail(), LogMatcher.EXCEPTION_REG);
    }

    @Override
    public String toString() {
        //格式化日志异常信息
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("####|");
        stringBuilder.append(super.getTime());
        stringBuilder.append("|");
        stringBuilder.append(super.getLevel());
        stringBuilder.append("|");
        stringBuilder.append(super.getModule());
        stringBuilder.append("|");
        stringBuilder.append(threadInfo);
        stringBuilder.append("|");
        stringBuilder.append(super.getDetail());
        stringBuilder.append("|####\n");
        return stringBuilder.toString();
    }

    public String getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}

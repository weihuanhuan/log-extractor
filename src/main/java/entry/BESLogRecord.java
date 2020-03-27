package entry;

import matcher.LogMatcher;
import util.Constants;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class BESLogRecord extends AbstractLogRecord {

    //4
    protected String threadInfo;

    public static final String prefix = "####|";
    public static final String split = "|";
    public static final String suffix = "|####\n";

    public BESLogRecord() {
        this(null, null, null, Constants.EMPTY_STRING, null);
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
        stringBuilder.append(getPrefix());
        stringBuilder.append(super.getTime());
        stringBuilder.append(getSplit());
        stringBuilder.append(super.getLevel());
        stringBuilder.append(getSplit());
        stringBuilder.append(super.getModule());
        stringBuilder.append(getSplit());
        stringBuilder.append(threadInfo);
        stringBuilder.append(getSplit());
        stringBuilder.append(super.getDetail());
        stringBuilder.append(getSuffix());
        return stringBuilder.toString();
    }

    public String getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(String threadInfo) {
        this.threadInfo = threadInfo;
    }

    public String getPrefix() {
        return BESLogRecord.prefix;
    }

    public String getSplit() {
        return BESLogRecord.split;
    }

    public String getSuffix() {
        return BESLogRecord.suffix;
    }

}

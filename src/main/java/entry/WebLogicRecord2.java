package entry;

import matcher.LogMatcher;
import util.Constants;

public class WebLogicRecord2 extends AbstractLogRecord {

    //4
    private String app;

    //5
    private String file;

    //6
    private String thread;

    //7
    private String source;

    //8
    private String blank1;

    //9
    private String blank2;

    //10
    private String timestamp;

    //11
    private String code;

    public WebLogicRecord2() {
        this.app = Constants.EMPTY_STRING;
        this.file = Constants.EMPTY_STRING;
        this.thread = Constants.EMPTY_STRING;
        this.source = Constants.EMPTY_STRING;
        this.blank1 = Constants.EMPTY_STRING;
        this.blank2 = Constants.EMPTY_STRING;
        this.timestamp = Constants.EMPTY_STRING;
        this.code = Constants.EMPTY_STRING;
    }

    public WebLogicRecord2(String time, String level, String module, String detail, String app, String file, String thread, String source, String blank1, String blank2, String timestamp, String code) {
        super(time, level, module, detail);
        this.app = app;
        this.file = file;
        this.thread = thread;
        this.source = source;
        this.blank1 = blank1;
        this.blank2 = blank2;
        this.timestamp = timestamp;
        this.code = code;
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
        stringBuilder.append("<");
        stringBuilder.append(super.getTime());
        stringBuilder.append("> <");
        stringBuilder.append(super.getLevel());
        stringBuilder.append("> <");
        stringBuilder.append(super.getModule());
        stringBuilder.append("> <");
        stringBuilder.append(app);
        stringBuilder.append("> <");
        stringBuilder.append(file);
        stringBuilder.append("> <");
        stringBuilder.append(thread);
        stringBuilder.append("> <");
        stringBuilder.append(source);
        stringBuilder.append("> <");
        stringBuilder.append(blank1);
        stringBuilder.append("> <");
        stringBuilder.append(blank2);
        stringBuilder.append("> <");
        stringBuilder.append(timestamp);
        stringBuilder.append("> <");
        stringBuilder.append(code);
        stringBuilder.append("> <");
        stringBuilder.append(super.getDetail());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBlank1() {
        return blank1;
    }

    public void setBlank1(String blank1) {
        this.blank1 = blank1;
    }

    public String getBlank2() {
        return blank2;
    }

    public void setBlank2(String blank2) {
        this.blank2 = blank2;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

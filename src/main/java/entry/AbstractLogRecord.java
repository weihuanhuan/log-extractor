package entry;

import util.Constants;

/**
 * Created by JasonFitch on 9/7/2019.
 */
abstract public class AbstractLogRecord implements LogRecord {

    //1
    private String time;

    //2
    private String level;

    //3
    private String module;

    //bes 5 weblogic 6  weblogic2 12
    private String detail;

    //extra 1
    private int lineNo;

    //extra 2
    private String filePath;

    public AbstractLogRecord() {
        time = Constants.EMPTY_STRING;
        level = Constants.EMPTY_STRING;
        module = Constants.EMPTY_STRING;
        detail = Constants.EMPTY_STRING;
        lineNo = -1;
        filePath = Constants.EMPTY_STRING;
    }

    public AbstractLogRecord(String time, String level, String module, String detail) {
        this.time = time;
        this.level = level;
        this.module = module;
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    abstract public boolean checkHasException();

    @Override
    public String toString() {
        //格式化日志位置信息
        return "Original log file absolute path: " + filePath + "\nOriginal log entry line number : " + lineNo + "\n";
    }
}

package entry;

import matcher.LogMatcher;
import util.Constants;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class WebLogicRecord extends AbstractLogRecord {

    //4
    private String code;

    //5
    private String info;

    public WebLogicRecord() {
        code = Constants.EMPTY_STRING;
        info = Constants.EMPTY_STRING;
    }

    public WebLogicRecord(String time, String level, String module, String code, String info, String detail) {
        super(time, level, module, detail);
        this.code = code;
        this.info = info;
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
        stringBuilder.append(code);
        stringBuilder.append("> <");
        stringBuilder.append(info);
        stringBuilder.append("> <");
        stringBuilder.append(super.getDetail());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}

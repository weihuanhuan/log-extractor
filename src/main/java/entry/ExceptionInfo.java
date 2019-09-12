package entry;

/**
 * Created by JasonFitch on 9/12/2019.
 */
public class ExceptionInfo extends AbstractLogRecord {

    public ExceptionInfo() {
    }

    public ExceptionInfo(String time, String level, String module, String threadInfo, String detail) {
        super(time, level, module, detail);
    }

    @Override
    public boolean checkHasException() {
        return true;
    }

    @Override
    public String toString() {
        //格式化日志异常信息
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(super.getDetail());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}

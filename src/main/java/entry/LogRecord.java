package entry;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public interface LogRecord {

    String getDetail();

    void setDetail(String detail);

    boolean checkHasException();
}

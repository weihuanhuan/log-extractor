package result;

import entry.LogRecord;

import java.util.ArrayList;
import java.util.List;

//用来保存的结果
public class StatisticResult implements Result {

    private String fileNamePath;

    private List<LogRecord> exceptionList = new ArrayList<>();

    private Accumulator accumulator = new Accumulator();

    public String getFileNamePath() {
        return fileNamePath;
    }

    public void setFileNamePath(String fileNamePath) {
        this.fileNamePath = fileNamePath;
    }

    public List<LogRecord> getExceptionList() {
        return exceptionList;
    }

    public void setExceptionList(List<LogRecord> exceptionList) {
        this.exceptionList = exceptionList;
    }

    public Accumulator getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(Accumulator accumulator) {
        this.accumulator = accumulator;
    }
}
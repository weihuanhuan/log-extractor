package task;

import result.Result;
import util.ResultUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WriteTask extends Task<List<File>> {

    private List<Result> results;
    private List<File> excelFile;
    private int matchLengthInt;
    private boolean captureExcelBool;

    public WriteTask(String fileCanonicalPath, List<Result> results, int matchLengthInt, boolean captureExcelBool) {
        super(fileCanonicalPath);
        this.results = results;
        this.matchLengthInt = matchLengthInt;
        this.captureExcelBool = captureExcelBool;
    }

    @Override
    public void doRun() {
        try {
            excelFile = ResultUtils.writeResults(results, matchLengthInt, captureExcelBool);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<File> getResult() {
        return excelFile;
    }
}

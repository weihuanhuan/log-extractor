package task;

import util.ResultUtils;

import java.io.File;
import java.util.List;

public class MergeTask extends Task<File> {

    private List<File> resultFiles;
    private String outDir;
    private File file;

    public MergeTask(String fileCanonicalPath, List<File> resultFiles, String outDir) {
        super(fileCanonicalPath);
        this.resultFiles = resultFiles;
        this.outDir = outDir;
    }

    @Override
    public void doRun() {
        file = ResultUtils.writeMergedResults(resultFiles, outDir);
    }

    @Override
    public File getResult() {
        //取回结果
        return file;
    }

}

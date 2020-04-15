package task;

import interceptor.Interceptor;
import parser.LogParser;
import result.Result;

import java.util.ArrayList;
import java.util.List;

public class AnalysisTask extends Task<List<Result>> {

    private LogParser logParser;
    private Interceptor[] interceptors;

    public AnalysisTask(String fileCanonicalPath, LogParser logParser, Interceptor[] interceptors) {
        super(fileCanonicalPath);
        this.logParser = logParser;
        this.interceptors = interceptors;
    }

    @Override
    public void doRun() {
        //解析
        logParser.parse();
    }

    @Override
    public List<Result> getResult() {
        //取回结果
        List<Result> results = new ArrayList<>();
        for (Interceptor interceptor : interceptors) {
            Result result = interceptor.getResult();
            if (result != null) {
                results.add(result);
            }
        }
        return results;
    }

}

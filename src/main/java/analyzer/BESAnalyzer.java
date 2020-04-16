package analyzer;

import bootstrap.LogCommandLineRuntime;
import interceptor.ExceptionInfoPairInterceptor;
import parser.BESLogParser;
import result.StatisticResult;

public class BESAnalyzer extends AbstractAnalyzer {

    public BESAnalyzer(LogCommandLineRuntime lineRuntime) {
        super(lineRuntime);
    }

    @Override
    public void initAnalyzer(String fileCanonicalPath) {
        //确定目标解析方案
        this.setLogParser(getBESLogParser(lineRuntime.getLogEncoding()));

        //确定目标处理方案
        StatisticResult result = new StatisticResult(lineRuntime.getCompressLength());
        result.setFileNamePath(fileCanonicalPath);
        this.addInterceptors(new ExceptionInfoPairInterceptor(lineRuntime, result));
    }

    protected BESLogParser getBESLogParser(String encoding) {
        return new BESLogParser(encoding);
    }

}

package analyzer;

import bootstrap.LogCommandLineRuntime;
import interceptor.ExceptionInfoPairInterceptor;
import parser.WebLogicLogParser2;
import result.StatisticResult;

public class WebLogicAnalyzer2 extends AbstractAnalyzer {

    public WebLogicAnalyzer2(LogCommandLineRuntime lineRuntime) {
        super(lineRuntime);
    }

    @Override
    public void initAnalyzer(String fileCanonicalPath) {
        //确定目标处理方案
        this.setLogParser(new WebLogicLogParser2(lineRuntime.getLogEncoding()));

        //确定目标处理方案
        StatisticResult result = new StatisticResult(lineRuntime.getCompressLength());
        result.setFileNamePath(fileCanonicalPath);
        this.addInterceptors(new ExceptionInfoPairInterceptor(lineRuntime, result));
    }
}

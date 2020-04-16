package analyzer;

import bootstrap.LogCommandLineRuntime;
import interceptor.ExceptionLogRecordInterceptor;
import parser.ExceptionLogParser;
import result.StatisticResult;

/**
 * Created by JasonFitch on 9/11/2019.
 */
public class ExceptionAnalyzer extends AbstractAnalyzer implements Analyzer {

    public ExceptionAnalyzer(LogCommandLineRuntime lineRuntime) {
        super(lineRuntime);
    }

    @Override
    public void initAnalyzer(String fileCanonicalPath) {
        //确定目标解析方案
        this.setLogParser(new ExceptionLogParser(lineRuntime.getLogEncoding(), lineRuntime.getMatchLength()));

        //确定目标处理方案
        StatisticResult result = new StatisticResult(lineRuntime.getCompressLength());
        result.setFileNamePath(fileCanonicalPath);
        this.addInterceptors(new ExceptionLogRecordInterceptor(result));

    }
}

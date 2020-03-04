package analyzer;

import interceptor.ExceptionInfoPairInterceptor;
import parser.BESLogParser;
import result.StatisticResult;

import java.util.List;

public class BESAnalyzer extends AbstractAnalyzer {

    public BESAnalyzer(List<String> logFileList, String logEncoding, int matchLength) {
        super(logFileList, logEncoding, matchLength);
    }

    @Override
    public void initAnalyzer(String fileCanonicalPath) {
        //确定目标解析方案
        this.setLogParser(new BESLogParser(logEncoding));

        //确定目标处理方案
        StatisticResult result = new StatisticResult();
        result.setFileNamePath(fileCanonicalPath);
        this.addInterceptors(new ExceptionInfoPairInterceptor(matchLength, result));
    }

}

package analyzer;

import interceptor.ExceptStatisticInter;
import interceptor.ExceptionNameStatistic;
import parser.WebLogicLogParser;
import result.StatisticResult;

import java.util.List;

public class WebLogicAnalyzer extends AbstractAnalyzer {

    public WebLogicAnalyzer(List<String> logFileList, String logEncoding, int matchLength) {
        super(logFileList, logEncoding, matchLength);
    }

    @Override
    public void initAnalyzer(String fileCanonicalPath) {
        //确定目标处理方案
        this.setLogParser(new WebLogicLogParser(logEncoding));

        //确定目标处理方案
        StatisticResult result = new StatisticResult();
        result.setFileNamePath(fileCanonicalPath);
        this.addInterceptors(new ExceptionNameStatistic(matchLength, result));

    }
}

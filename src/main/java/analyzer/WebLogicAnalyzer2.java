package analyzer;

import interceptor.ExceptStatisticInter;
import parser.WebLogicLogParser2;
import result.StatisticResult;

import java.util.List;

public class WebLogicAnalyzer2 extends AbstractAnalyzer {

    public WebLogicAnalyzer2(List<String> logFileList, String logEncoding, int matchLength) {
        super(logFileList, logEncoding, matchLength);
    }

    @Override
    public void initAnalyzer(String fileCanonicalPath) {
        //确定目标处理方案
        this.setLogParser(new WebLogicLogParser2(logEncoding));

        //确定目标处理方案
        StatisticResult result = new StatisticResult();
        result.setFileNamePath(fileCanonicalPath);
        this.addInterceptors(new ExceptStatisticInter(matchLength, result));
    }
}

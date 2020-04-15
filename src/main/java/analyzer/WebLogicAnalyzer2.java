package analyzer;

import interceptor.ExceptionInfoPairInterceptor;
import parser.WebLogicLogParser2;
import result.StatisticResult;

import java.util.List;

public class WebLogicAnalyzer2 extends AbstractAnalyzer {

    public WebLogicAnalyzer2(List<String> logFileList, String logEncoding, int matchLength, boolean captureExcelBool, String outDir, int compressDigitalLength) {
        super(logFileList, logEncoding, matchLength, compressDigitalLength, captureExcelBool, outDir);
    }

    @Override
    public void initAnalyzer(String fileCanonicalPath) {
        //确定目标处理方案
        this.setLogParser(new WebLogicLogParser2(logEncoding));

        //确定目标处理方案
        StatisticResult result = new StatisticResult(compressDigitalLength);
        result.setFileNamePath(fileCanonicalPath);
        this.addInterceptors(new ExceptionInfoPairInterceptor(matchLength, result));
    }
}

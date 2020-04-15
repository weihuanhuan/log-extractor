package analyzer;

import interceptor.ExceptionLogRecordInterceptor;
import java.util.List;
import parser.ExceptionLogParser;
import result.StatisticResult;

/**
 * Created by JasonFitch on 9/11/2019.
 */
public class ExceptionAnalyzer extends AbstractAnalyzer implements Analyzer {

    public ExceptionAnalyzer(List<String> logFileList, String logEncoding, int matchLength, boolean captureExcelBool, String outDir, int compressDigitalLength) {
        super(logFileList, logEncoding, matchLength, compressDigitalLength, captureExcelBool, outDir);
    }

    @Override
    public void initAnalyzer(String fileCanonicalPath) {
        //确定目标解析方案
        this.setLogParser(new ExceptionLogParser(logEncoding,matchLength));

        //确定目标处理方案
        StatisticResult result = new StatisticResult(compressDigitalLength);
        result.setFileNamePath(fileCanonicalPath);
        this.addInterceptors(new ExceptionLogRecordInterceptor(result));

    }
}

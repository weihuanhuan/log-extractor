package analyzer;

import java.util.List;
import parser.ExceptionParser;

/**
 * Created by JasonFitch on 9/11/2019.
 */
public class ExceptionAnalyzer extends AbstractAnalyzer implements Analyzer {

    public ExceptionAnalyzer(List<String> logFileList, String logEncoding, int matchLength) {
        super(logFileList, logEncoding, matchLength);
    }

    @Override
    public void initAnalyzer(String fileCanonicalPath) {
        //确定目标解析方案
        this.setLogParser(new ExceptionParser(logEncoding));


    }
}

package analyzer;

import java.util.List;
import parser.BESLogParser;
import parser.BESLogParser95;

public class BESAnalyzer95 extends BESAnalyzer {

    public BESAnalyzer95(List<String> logFileList, String logEncoding, int matchLength, int compressDigitalLength) {
        super(logFileList, logEncoding, matchLength, compressDigitalLength);
    }

    @Override
    protected BESLogParser getBESLogParser(String encoding) {
        return new BESLogParser95(encoding);
    }
}

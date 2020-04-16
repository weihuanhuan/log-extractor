package analyzer;

import bootstrap.LogCommandLineRuntime;
import parser.BESLogParser;
import parser.BESLogParser95;

public class BESAnalyzer95 extends BESAnalyzer {

    public BESAnalyzer95(LogCommandLineRuntime lineRuntime) {
        super(lineRuntime);
    }

    @Override
    protected BESLogParser getBESLogParser(String encoding) {
        return new BESLogParser95(encoding);
    }
}

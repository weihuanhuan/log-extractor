package parser;

import entry.BESLogRecord;
import entry.BESLogRecord95;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class BESLogParser95 extends BESLogParser {

    public BESLogParser95(String encoding) {
        super(encoding);
    }

    @Override
    protected BESLogRecord newBESLogRecord() {
        // bes95
        // 标准bes95日志格式  ##| time | level | module | thread | detail |##
        return new BESLogRecord95();
    }

}

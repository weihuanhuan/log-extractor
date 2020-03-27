package parser;

import entry.BESLogRecord;
import reader.InputStreamFileReader;
import reader.Mark;
import util.Constants;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class BESLogParser extends AbstractLogParser {

    protected String prefix;
    protected String split;
    protected String suffix;

    public BESLogParser() {
        this(Constants.DEFAULT_LOG_ENCODING);
    }

    public BESLogParser(String encoding) {
        this.reader = new InputStreamFileReader(encoding);
        BESLogRecord besLogRecord = newBESLogRecord();
        this.prefix = besLogRecord.getPrefix();
        this.split = besLogRecord.getSplit();
        this.suffix = besLogRecord.getSuffix();
    }

    @Override
    public void parse() {
        try {
            InputStreamFileReader reader = (InputStreamFileReader) this.reader;

            while (this.reader.hasMoreInput()) {

                Mark m = reader.skipUntil(prefix);
                if (m == null) {
                    break;
                }

                BESLogRecord record = newBESLogRecord();
                record.setLineNo(reader.getCurrent().line);
                record.setFilePath(reader.getCurrentFilePath());

                record.setTime(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setLevel(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setModule(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setThreadInfo(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setDetail(reader.readTextUtil(suffix));

                //调用处理器
                if (this.handler != null) {
                    this.handler.invoke(record);
                }
            }

        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    protected BESLogRecord newBESLogRecord() {
        // bes
        // 标准bes日志格式  ####| time | level | module | thread | detail |####
        return new BESLogRecord();
    }

}

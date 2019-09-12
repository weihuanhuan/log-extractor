package parser;

import entry.BESLogRecord;
import reader.InputStreamFileReader;
import reader.Mark;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class BESLogParser extends AbstractLogParser {

    public BESLogParser() {
        this.reader = new InputStreamFileReader();
    }

    public BESLogParser(String encoding) {
        this.reader = new InputStreamFileReader(encoding);
    }

    @Override
    public void parse() {
        try {
            InputStreamFileReader reader = (InputStreamFileReader) this.reader;

            // bes
            // 标准bes日志格式  ####| time | level | module | thread | detail |####

            //定义解析标记
            String prefix = "####|";
            String split = "|";
            String suffix = "|####\n";
            while (this.reader.hasMoreInput()) {

                Mark m = reader.skipUntil(prefix);
                if (m == null) {
                    break;
                }

                BESLogRecord record = new BESLogRecord();
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

}

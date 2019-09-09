package parser;

import entry.BESLogRecord;
import reader.Mark;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class BESLogParser extends AbstractLogParser {

    public BESLogParser() {
    }

    public BESLogParser(String encoding) {
        super(encoding);
    }

    @Override
    public void parse() {
        try {

            // bes
            // 标准bes日志格式  ####| time | level | module | thread | detail |####

            //定义解析标记
            String prefix = "####|";
            String split = "|";
            String suffix = "|####\n";
            while (this.reader.hasMoreInput()) {

                Mark m = this.reader.skipUntil(prefix);
                if (m == null) {
                    break;
                }

                BESLogRecord record = new BESLogRecord();
                record.setLineNo(this.reader.getCurrent().line);
                record.setFilePath(this.reader.getCurrentFilePath());

                record.setTime(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setLevel(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setModule(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setThreadInfo(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setDetail(this.reader.readTextUtil(suffix));

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

package parser;

import entry.WebLogicRecord2;
import reader.Mark;

public class WebLogicLogParser2 extends AbstractLogParser {

    public WebLogicLogParser2() {
    }

    public WebLogicLogParser2(String encoding) {
        super(encoding);
    }

    @Override
    public void parse() {
        try {

            // weblogic2
            // weblogic2日志格式 < time > < level > < module > < app > < file > < thread > < source > < blank1 > < blank2 > < timestamp > < code > < detail >

            //定义解析标记
            String prefix = "####<";
            String split = "> <";
            String suffix = "> \n";
            while (this.reader.hasMoreInput()) {
                Mark m = this.reader.skipUntil(prefix);
                if (m == null) {
                    break;
                }

                WebLogicRecord2 record = new WebLogicRecord2();
                record.setLineNo(this.reader.getCurrent().line);
                record.setFilePath(this.reader.getCurrentFilePath());

                record.setTime(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setLevel(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setModule(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setApp(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setFile(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setThread(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setSource(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setBlank1(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setBlank2(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setTimestamp(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setCode(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setDetail(this.reader.readTextUtil(suffix));

                //调用处理器
                if (this.handler != null)
                    this.handler.invoke(record);
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }


}
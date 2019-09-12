package parser;

import entry.WebLogicRecord2;
import reader.InputStreamFileReader;
import reader.Mark;

public class WebLogicLogParser2 extends AbstractLogParser {

    public WebLogicLogParser2() {
        this.reader = new InputStreamFileReader();
    }

    public WebLogicLogParser2(String encoding) {
        this.reader = new InputStreamFileReader(encoding);
    }

    @Override
    public void parse() {
        try {
            InputStreamFileReader reader = (InputStreamFileReader) this.reader;

            // weblogic2
            // weblogic2日志格式 < time > < level > < module > < app > < file > < thread > < source > < blank1 > < blank2 > < timestamp > < code > < detail >

            //定义解析标记
            String prefix = "####<";
            String split = "> <";
            String suffix = "> \n";
            while (this.reader.hasMoreInput()) {
                Mark m = reader.skipUntil(prefix);
                if (m == null) {
                    break;
                }

                WebLogicRecord2 record = new WebLogicRecord2();
                record.setLineNo(reader.getCurrent().line);
                record.setFilePath(reader.getCurrentFilePath());

                record.setTime(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setLevel(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setModule(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setApp(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setFile(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setThread(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setSource(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setBlank1(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setBlank2(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setTimestamp(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setCode(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setDetail(reader.readTextUtil(suffix));

                //调用处理器
                if (this.handler != null)
                    this.handler.invoke(record);
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }


}
package parser;

import entry.WebLogicRecord;
import reader.InputStreamFileReader;
import reader.Mark;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class WebLogicLogParser extends AbstractLogParser {

    public WebLogicLogParser() {
        this.reader = new InputStreamFileReader();
    }


    public WebLogicLogParser(String encoding) {
        this.reader = new InputStreamFileReader(encoding);
    }

    @Override
    public void parse() {
        try {
            InputStreamFileReader reader = (InputStreamFileReader) this.reader;

            // weblogic
            // weblogic日志格式 < time > < level > < module > < code > < info > detail

            //定义解析标记
            String prefix = "<";
            String split = "> <";
            String lastSplit = "> ";
            String suffix = "\n<";
            while (this.reader.hasMoreInput()) {

                Mark m = reader.skipUntil(prefix);
                if (m == null) {
                    break;
                }

                WebLogicRecord record = new WebLogicRecord();
                record.setLineNo(reader.getCurrent().line);
                record.setFilePath(reader.getCurrentFilePath());

                record.setTime(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setLevel(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setModule(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setCode(reader.readTextUtil(split));

                reader.nextChars(split);
                record.setInfo(reader.readTextUtil(lastSplit));

                reader.nextChars(lastSplit);
                record.setDetail(reader.readTextUtil(suffix));

                reader.pushBackChar();

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

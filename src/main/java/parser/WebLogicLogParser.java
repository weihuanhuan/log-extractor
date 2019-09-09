package parser;

import entry.WebLogicRecord;
import reader.Mark;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class WebLogicLogParser extends AbstractLogParser {

    public WebLogicLogParser() {
    }

    public WebLogicLogParser(String encoding) {
        super(encoding);
    }

    @Override
    public void parse() {
        try {

            // weblogic
            // weblogic日志格式 < time > < level > < module > < code > < info > detail

            //定义解析标记
            String prefix = "<";
            String split = "> <";
            String lastSplit = "> ";
            String suffix = "\n<";
            while (this.reader.hasMoreInput()) {

                Mark m = this.reader.skipUntil(prefix);
                if (m == null) {
                    break;
                }

                WebLogicRecord record = new WebLogicRecord();
                record.setLineNo(this.reader.getCurrent().line);
                record.setFilePath(this.reader.getCurrentFilePath());

                record.setTime(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setLevel(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setModule(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setCode(this.reader.readTextUtil(split));

                this.reader.nextChars(split);
                record.setInfo(this.reader.readTextUtil(lastSplit));

                this.reader.nextChars(lastSplit);
                record.setDetail(this.reader.readTextUtil(suffix));

                this.reader.pushBackChar();

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

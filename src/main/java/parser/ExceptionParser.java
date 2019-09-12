package parser;

import entry.ExceptionInfo;
import reader.RandomAccessFileReader;
import util.Constants;

/**
 * Created by JasonFitch on 9/11/2019.
 */
public class ExceptionParser extends AbstractLogParser implements LogParser {

    private int suffixMatchLength = Integer.valueOf(Constants.DEFAULT_MATCH_LENGTH);

    public ExceptionParser() {
        this.reader = new RandomAccessFileReader();
    }

    public ExceptionParser(String encoding, int suffixMatchLength) {
        this.reader = new RandomAccessFileReader(encoding);
        this.suffixMatchLength = suffixMatchLength;
    }

    @Override
    public void parse() {

        try {
            RandomAccessFileReader reader = (RandomAccessFileReader) this.reader;

            String prefix;
            String suffix;
            while (this.reader.hasMoreInput()) {
                String currentLine = reader.readNextLine();

                boolean atCausedBy = currentLine.startsWith("Caused by: ");
                if (atCausedBy) {
                    continue;
                }
                boolean atLine = currentLine.startsWith("\tat ");
                if (atLine) {
                    continue;
                }
                int exception = currentLine.indexOf("Exception: ");
                if (exception <= -1) {
                    continue;
                }

                //记录行号
                int lineNo = reader.getLineNo();

                //prefix
                prefix = currentLine.substring(0, exception);
                //异常名前大都有空格 & beslog 分隔符
                int j = prefix.lastIndexOf(" ");
                int k = prefix.lastIndexOf("|");
                if (k > j) {
                    j = k;
                }
                if (j > -1) {
                    prefix = prefix.substring(j + 1);
                }

                //suffix
                int remainLength = currentLine.length() - exception;
                if (remainLength >= suffixMatchLength) {
                    suffix = currentLine.substring(exception, exception + suffixMatchLength);
                } else {
                    suffix = currentLine.substring(exception) + reader.readStringBytes(suffixMatchLength - remainLength);
                }
                int i = suffix.indexOf("\tat ");
                if (i > -1) {
                    suffix = suffix.substring(0, i);
                }

                //去掉无用的可能空白字符
                StringBuilder sb = new StringBuilder();
                sb.append(prefix);
                sb.append(suffix);
                String trim = sb.toString().trim();
                if (trim.isEmpty()) {
                    continue;
                }

                ExceptionInfo record = new ExceptionInfo();
                record.setLineNo(lineNo);
                record.setFilePath(reader.getCurrentFilePath());
                record.setDetail(trim);

                //调用处理器
                if (this.handler != null) {
                    this.handler.invoke(record);
                }

            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
